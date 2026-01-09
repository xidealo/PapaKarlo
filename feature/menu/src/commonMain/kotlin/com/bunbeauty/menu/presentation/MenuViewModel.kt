package com.bunbeauty.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.menu.AddMenuProductClickEvent
import com.bunbeauty.analytic.event.menu.LoadedMenuEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.analytic.parameter.TimeParameter
import com.bunbeauty.core.Logger
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.core.domain.menu_product.IMenuProductInteractor
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.menu.MenuSection
import com.bunbeauty.core.model.mapper.toMenuItemList
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.menu.presentation.model.MenuDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.measureTime

private const val MAIN_MENU_VIEW_MODEL_TAG = "MenuViewModel"

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val analyticService: AnalyticService,
) : ViewModel() {
    private val mutableMenuState =
        MutableStateFlow(
            MenuDataState(
                categoryItemList = emptyList(),
                cartCostAndCount = null,
                menuItemList = emptyList(),
                state = MenuDataState.State.Loading,
                userScrollEnabled = true,
                eventList = emptyList(),
            ),
        )
    val menuState = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    init {
        getMenu()
        observeCart()
    }

    fun onStartAutoScroll() {
        mutableMenuState.update {
            it.copy(userScrollEnabled = false)
        }
    }

    fun onStopAutoScroll() {
        mutableMenuState.update {
            it.copy(userScrollEnabled = true)
        }
    }

    private fun observeCart() {
        viewModelScope.launchSafe(
            block = {
                observeCartUseCase().collectLatest { cartTotalAndCount ->
                    mutableMenuState.update { state ->
                        state.copy(cartCostAndCount = cartTotalAndCount)
                    }
                }
            },
            onError = { throwable ->
                handleError(throwable)
            },
        )
    }

    fun getMenu() {
        Logger.logD(MAIN_MENU_VIEW_MODEL_TAG, "getMenu")

        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuDataState.State.Loading,
            )
        }

        viewModelScope.launchSafe(
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
                        mutableMenuState.update { oldState ->
                            oldState.copy(
                                categoryItemList =
                                    menuSectionList.map { menuSection ->
                                        toCategoryItemModel(menuSection)
                                    },
                                menuItemList = menuItemList,
                                state = MenuDataState.State.Success,
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

    fun onCategoryClicked(categoryItem: CategoryItem) {
        setCategory(categoryItem.uuid)
    }

    fun onMenuPositionChanged(menuPosition: Int) {
        if (!mutableMenuState.value.userScrollEnabled || menuPosition == currentMenuPosition) {
            return
        }

        currentMenuPosition = menuPosition

        viewModelScope.launchSafe(
            block = {
                val menuItemModelList = mutableMenuState.value.menuItemList
                menuItemModelList
                    .filterIsInstance<MenuItem.CategoryHeader>()
                    .findLast { menuItemModel ->
                        menuItemModelList.indexOf(menuItemModel) <= menuPosition
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
        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuDataState.State.Error(throwable),
            )
        }
    }

    fun onMenuItemClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        mutableMenuState.update { oldState ->
            oldState +
                MenuDataState.Event.GoToSelectedItem(
                    uuid = menuProduct.uuid,
                    name = menuProduct.name,
                )
        }
    }

    private fun findMenuProduct(uuid: String): MenuItem.Product? =
        mutableMenuState.value.menuItemList
            .filterIsInstance<MenuItem.Product>()
            .find { menuItem ->
                (menuItem.uuid == uuid)
            }

    fun onAddProductClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        analyticService.sendEvent(
            event =
                AddMenuProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProduct.uuid),
                ),
        )
        viewModelScope.launchSafe(
            block = {
                if (menuProduct.hasAdditions) {
                    mutableMenuState.update { oldState ->
                        oldState +
                            MenuDataState.Event.GoToSelectedItem(
                                uuid = menuProduct.uuid,
                                name = menuProduct.name,
                            )
                    }
                } else {
                    addMenuProductUseCase(menuProductUuid = menuProduct.uuid)
                }
            },
            onError = {
                mutableMenuState.update { oldState ->
                    oldState + MenuDataState.Event.ShowAddProductError
                }
            },
        )
    }

    fun getMenuListPosition(categoryItem: CategoryItem): Int {
        val index =
            mutableMenuState.value.menuItemList.indexOfFirst { menuItemModel ->
                (menuItemModel as? MenuItem.CategoryHeader)?.uuid == categoryItem.uuid
            }
        return if (index == 1 && mutableMenuState.value.hasDiscountItem) {
            0
        } else {
            index
        }
    }

    private fun setCategory(categoryUuid: String) {
        viewModelScope.launch {
            if (selectedCategoryUuid == categoryUuid) {
                return@launch
            }
            selectedCategoryUuid = categoryUuid

            val categoryItemModelList =
                mutableMenuState.value.categoryItemList.map { categoryItemModel ->
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
            mutableMenuState.update { oldState ->
                oldState.copy(
                    categoryItemList = categoryItemModelList,
                    menuItemList = oldState.menuItemList,
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

    fun consumeEventList(eventList: List<MenuDataState.Event>) {
        mutableMenuState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
