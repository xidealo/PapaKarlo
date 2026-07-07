package com.bunbeauty.menu.presentation

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.menu.AddMenuProductClickEvent
import com.bunbeauty.analytic.event.menu.LoadedMenuEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.analytic.parameter.TimeParameter
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Constants.FAVORITES_CATEGORY_UUID
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.core.domain.auth.ObserveTokenUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.favorite.GetFavoriteMenuProductsUseCase
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.core.domain.menu_product.IMenuProductInteractor
import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.StopObserveOrdersUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.mapper.toMenuItemList
import com.bunbeauty.core.model.mapper.toMenuProductItem
import com.bunbeauty.core.model.menu.MenuSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlin.time.measureTime

private const val MAIN_MENU_VIEW_MODEL_TAG = "MenuViewModel"

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val analyticService: AnalyticService,
    private val observeLastOrderUseCase: ObserveLastOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
    private val getLastOrderUseCase: GetLastOrderUseCase,
    private val observeTokenUseCase: ObserveTokenUseCase,
    private val getFavoriteMenuProductsUseCase: GetFavoriteMenuProductsUseCase,
) : SharedStateViewModel<MenuState.DataState, MenuState.Action, MenuState.Event>(
        initDataState =
            MenuState.DataState(
                categoryItemList = emptyList(),
                cartCostAndCount = null,
                menuItemList = emptyList(),
                favoriteProductList = emptyList(),
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
        observeToken()
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
            MenuState.Action.RefreshFavorites -> refreshFavoriteProducts()
            MenuState.Action.ScrollToTop -> scrollToTop()
        }
    }

    private fun scrollToTop() {
        currentMenuPosition = 0
        val favoriteProductList = mutableDataState.value.favoriteProductList
        val defaultCategoryUuid =
            if (favoriteProductList.isNotEmpty()) {
                FAVORITES_CATEGORY_UUID
            } else {
                mutableDataState.value
                    .categoryItemList
                    .firstOrNull()
                    ?.uuid
            }
        defaultCategoryUuid?.let { categoryUuid ->
            setCategory(categoryUuid)
        }
        setState {
            copy(scrollToTopRequest = scrollToTopRequest + 1)
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

    private fun observeToken() {
        sharedScope.launchSafe(
            block = {
                observeTokenUseCase()
                    .drop(1)
                    .distinctUntilChanged()
                    .collectLatest {
                        refreshDiscount()
                        refreshFavoriteProducts()
                    }
            },
            onError = { throwable ->
                Logger.logE(MAIN_MENU_VIEW_MODEL_TAG, throwable.stackTraceToString())
            },
        )
    }

    private fun refreshDiscount() {
        sharedScope.launchSafe(
            block = {
                val discountItem =
                    getDiscountUseCase()?.firstOrderDiscount?.toString()?.let { discount ->
                        MenuItem.Discount(discount = discount)
                    }
                setState {
                    copy(
                        menuItemList =
                            listOfNotNull(discountItem) +
                                menuItemList.filterNot { menuItem ->
                                    menuItem is MenuItem.Discount
                                },
                    )
                }
            },
            onError = { throwable ->
                Logger.logE(MAIN_MENU_VIEW_MODEL_TAG, throwable.stackTraceToString())
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
                        val favoriteProductList = loadFavoriteProductList()

                        if (selectedCategoryUuid == null) {
                            selectedCategoryUuid =
                                if (favoriteProductList.isNotEmpty()) {
                                    FAVORITES_CATEGORY_UUID
                                } else {
                                    menuSectionList.firstOrNull()?.category?.uuid
                                }
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
                                categoryItemList = buildCategoryItemList(
                                    menuSectionList = menuSectionList,
                                    favoriteProductList = favoriteProductList,
                                ),
                                favoriteProductList = favoriteProductList,
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
        val hasFavoritesSection = mutableDataState.value.favoriteProductList.isNotEmpty()

        if (hasFavoritesSection && menuPosition == MENU_GRID_INDEX_FAVORITES) {
            setCategory(FAVORITES_CATEGORY_UUID)
            return
        }

        val menuListPosition =
            (menuPosition - menuContentStartGridIndex(hasFavoritesSection = hasFavoritesSection))
                .coerceAtLeast(0)

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
        mutableDataState.value.favoriteProductList.find { menuProduct ->
            menuProduct.uuid == uuid
        } ?: mutableDataState.value.menuItemList
            .filterIsInstance<MenuItem.Product>()
            .find { menuProduct ->
                menuProduct.uuid == uuid
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

    private fun toFavoritesCategoryItem(): CategoryItem =
        CategoryItem(
            key = "CategoryItemModel $FAVORITES_CATEGORY_UUID",
            uuid = FAVORITES_CATEGORY_UUID,
            name = "",
            isSelected = isCategorySelected(FAVORITES_CATEGORY_UUID),
        )

    private fun buildCategoryItemList(
        menuSectionList: List<MenuSection>,
        favoriteProductList: List<MenuItem.Product>,
    ): List<CategoryItem> {
        val menuCategoryList =
            menuSectionList.map { menuSection ->
                toCategoryItemModel(menuSection)
            }

        return if (favoriteProductList.isNotEmpty()) {
            listOf(toFavoritesCategoryItem()) + menuCategoryList
        } else {
            menuCategoryList
        }
    }

    private suspend fun loadFavoriteProductList(): List<MenuItem.Product> =
        getFavoriteMenuProductsUseCase().map { menuProduct ->
            menuProduct.toMenuProductItem()
        }

    private fun refreshFavoriteProducts() {
        sharedScope.launchSafe(
            block = {
                val favoriteProductList = loadFavoriteProductList()
                val menuSectionList = menuProductInteractor.getMenuSectionList()

                if (selectedCategoryUuid == FAVORITES_CATEGORY_UUID && favoriteProductList.isEmpty()) {
                    selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
                }

                setState {
                    copy(
                        favoriteProductList = favoriteProductList,
                        categoryItemList =
                            buildCategoryItemList(
                                menuSectionList = menuSectionList,
                                favoriteProductList = favoriteProductList,
                            ),
                    )
                }
            },
            onError = { throwable ->
                Logger.logE(MAIN_MENU_VIEW_MODEL_TAG, throwable.stackTraceToString())
            },
        )
    }

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
