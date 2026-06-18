package com.bunbeauty.menu.presentation

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.menu.AddMenuProductClickEvent
import com.bunbeauty.analytic.event.menu.LoadedMenuEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.analytic.parameter.TimeParameter
import com.bunbeauty.core.Logger
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.core.domain.menu_product.IMenuProductInteractor
import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.StopObserveOrdersUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.mapper.toMenuItemList
import com.bunbeauty.core.model.menu.MenuSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.measureTime

private const val MAIN_MENU_VIEW_MODEL_TAG = "MenuViewModel"

// Grid: 0=TopBar, 1=CategoryRow, 2=LastOrder, 3+=menuItemList
internal const val MENU_FIRST_CONTENT_GRID_INDEX = 3

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val analyticService: AnalyticService,
    private val observeLastOrderUseCase: ObserveLastOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
    private val getLastOrderUseCase: GetLastOrderUseCase,
) : SharedStateViewModel<MenuState.DataState, MenuState.Action, MenuState.Event>(
        initDataState =
            MenuState.DataState(
                categoryItemList = emptyList(),
                cartCostAndCount = null,
                menuItemList = emptyList(),
                state = MenuState.DataState.State.LOADING,
                userScrollEnabled = true,
                lastOrder = null,
            ),
    ) {
    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    private var observeLastOrderJob: Job? = null
    private var orderObservationUuid: String? = null

    init {
        getMenu()
        observeCart()
        sharedScope.launch {
            val lastOrder = getLastOrderUseCase()
            setState {
                copy(lastOrder = lastOrder)
            }
        }
    }

    override fun reduce(
        action: MenuState.Action,
        dataState: MenuState.DataState,
    ) {
        when (action) {
            MenuState.Action.OnRefreshClicked -> getMenu()
            is MenuState.Action.OnCategoryClicked -> setCategory(action.categoryItem.uuid)
            is MenuState.Action.OnMenuPositionChanged -> onMenuPositionChanged(action.menuPosition)
            MenuState.Action.OnStartAutoScroll -> onStartAutoScroll()
            MenuState.Action.OnStopAutoScroll -> onStopAutoScroll()
            is MenuState.Action.OnAddProductClicked -> onAddProductClicked(action.menuProductUuid)
            is MenuState.Action.OnMenuItemClicked -> onMenuItemClicked(action.menuProductUuid)
            is MenuState.Action.OnLastOrderClicked -> onLastOrderClicked(action.uuid)
            MenuState.Action.OnProfileClicked -> onProfileClicked()
            MenuState.Action.OnCartClicked -> onCartClicked()
            MenuState.Action.StartLastOrderObservation -> startLastOrderObservation()
            MenuState.Action.StopLastOrderObservation -> stopLastOrderObservation()
        }
    }

    private fun onStartAutoScroll() {
        setState {
            copy(userScrollEnabled = false)
        }
    }

    private fun onStopAutoScroll() {
        setState {
            copy(userScrollEnabled = true)
        }
    }

    private fun observeCart() {
        sharedScope.launchSafe(
            block = {
                observeCartUseCase().collectLatest { cartTotalAndCount ->
                    setState {
                        copy(cartCostAndCount = cartTotalAndCount)
                    }
                }
            },
            onError = { throwable ->
                handleError(throwable)
            },
        )
    }

    private fun getMenu() {
        Logger.logD(MAIN_MENU_VIEW_MODEL_TAG, "getMenu")

        setState {
            copy(state = MenuState.DataState.State.LOADING)
        }

        sharedScope.launchSafe(
            block = {
                val time =
                    measureTime {
                        val menuSectionList = menuProductInteractor.getMenuSectionList()

                        if (selectedCategoryUuid == null) {
                            selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
                        }

                        val discountItem =
                            getDiscountUseCase()?.firstOrderDiscount?.toString()?.let { discount ->
                                MenuItem.Discount(discount = discount)
                            }
                        val menuItemList =
                            listOfNotNull(discountItem) +
                                menuSectionList.flatMap { menuSection ->
                                    menuSection.toMenuItemList()
                                }
                        setState {
                            copy(
                                categoryItemList =
                                    menuSectionList.map { menuSection ->
                                        toCategoryItemModel(menuSection)
                                    },
                                menuItemList = menuItemList,
                                state = MenuState.DataState.State.SUCCESS,
                            )
                        }
                    }
                analyticService.sendEvent(
                    event =
                        LoadedMenuEvent(
                            timeParameter =
                                TimeParameter(
                                    value = time.toString(),
                                ),
                        ),
                )
            },
            onError = { throwable ->
                handleError(throwable)
            },
        )
    }

    private fun onMenuPositionChanged(menuPosition: Int) {
        if (!mutableDataState.value.userScrollEnabled || menuPosition == currentMenuPosition) {
            return
        }

        currentMenuPosition = menuPosition
        val menuListPosition = (menuPosition - MENU_FIRST_CONTENT_GRID_INDEX).coerceAtLeast(0)

        sharedScope.launchSafe(
            block = {
                val menuItemModelList = mutableDataState.value.menuItemList
                menuItemModelList
                    .filterIsInstance<MenuItem.CategoryHeader>()
                    .findLast { menuItemModel ->
                        menuItemModelList.indexOf(menuItemModel) <= menuListPosition
                    }?.let { menuItemModel ->
                        setCategory(menuItemModel.uuid)
                    }
            },
            onError = { throwable ->
                handleError(throwable)
            },
        )
    }

    private fun handleError(throwable: Throwable) {
        Logger.logE(MAIN_MENU_VIEW_MODEL_TAG, throwable.stackTraceToString())
        setState {
            copy(state = MenuState.DataState.State.ERROR)
        }
    }

    private fun onMenuItemClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        addEvent {
            MenuState.Event.GoToSelectedItem(
                uuid = menuProduct.uuid,
                name = menuProduct.name,
            )
        }
    }

    private fun onLastOrderClicked(uuid: String) {
        addEvent {
            MenuState.Event.OpenOrderDetails(uuid)
        }
    }

    private fun onProfileClicked() {
        addEvent {
            MenuState.Event.OpenProfile
        }
    }

    private fun onCartClicked() {
        addEvent {
            MenuState.Event.OpenConsumerCart
        }
    }

    private fun findMenuProduct(uuid: String): MenuItem.Product? =
        mutableDataState.value.menuItemList
            .filterIsInstance<MenuItem.Product>()
            .find { menuItem ->
                menuItem.uuid == uuid
            }

    private fun onAddProductClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        analyticService.sendEvent(
            event =
                AddMenuProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProduct.uuid),
                ),
        )
        sharedScope.launchSafe(
            block = {
                if (menuProduct.hasAdditions) {
                    addEvent {
                        MenuState.Event.GoToSelectedItem(
                            uuid = menuProduct.uuid,
                            name = menuProduct.name,
                        )
                    }
                } else {
                    addMenuProductUseCase(menuProductUuid = menuProduct.uuid)
                    addEvent {
                        MenuState.Event.ShowAddedProduct(name = menuProduct.name)
                    }
                }
            },
            onError = {
                addEvent {
                    MenuState.Event.ShowAddProductError
                }
            },
        )
    }

    private fun setCategory(categoryUuid: String) {
        sharedScope.launch {
            if (selectedCategoryUuid == categoryUuid) {
                return@launch
            }
            selectedCategoryUuid = categoryUuid

            val categoryItemModelList =
                mutableDataState.value.categoryItemList.map { categoryItemModel ->
                    when {
                        categoryItemModel.isSelected -> {
                            categoryItemModel.copy(isSelected = false)
                        }

                        categoryItemModel.uuid == selectedCategoryUuid -> {
                            categoryItemModel.copy(isSelected = true)
                        }

                        else -> {
                            categoryItemModel
                        }
                    }
                }
            setState {
                copy(
                    categoryItemList = categoryItemModelList,
                    menuItemList = menuItemList,
                )
            }
        }
    }

    private fun toCategoryItemModel(menuSection: MenuSection): CategoryItem =
        CategoryItem(
            key = "CategoryItemModel ${menuSection.category.uuid}",
            uuid = menuSection.category.uuid,
            name = menuSection.category.name,
            isSelected = isCategorySelected(menuSection.category.uuid),
        )

    private fun isCategorySelected(categoryUuid: String): Boolean =
        selectedCategoryUuid?.let {
            selectedCategoryUuid == categoryUuid
        } ?: false

    private fun startLastOrderObservation() {
        if (observeLastOrderJob?.isActive == true) {
            return
        }
        observeLastOrderJob =
            sharedScope.launchSafe(
                block = {
                    val (uuid, lastOrderFlow) = observeLastOrderUseCase()
                    orderObservationUuid = uuid
                    lastOrderFlow.collectLatest { lightOrder ->
                        setState {
                            copy(lastOrder = lightOrder)
                        }
                    }
                },
                onError = { error ->
                    Logger.logE(MAIN_MENU_VIEW_MODEL_TAG, error.stackTraceToString())
                },
                dispatcher = Dispatchers.Default,
            )
    }

    private fun stopLastOrderObservation() {
        observeLastOrderJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launch(Dispatchers.Default) {
                stopObserveOrdersUseCase(uuid)
            }
        }
        orderObservationUuid = null
    }
}
