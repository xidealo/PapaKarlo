package com.bunbeauty.shared.presentation.menu

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.menu.AddMenuProductClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.feature.menu.AddMenuProductUseCase
import com.bunbeauty.shared.domain.feature.orderavailable.GetIsOrderAvailableUseCase
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedViewModel
import com.bunbeauty.shared.presentation.menu.mapper.toMenuItemList
import com.bunbeauty.shared.presentation.menu.model.CategoryItem
import com.bunbeauty.shared.presentation.menu.model.MenuDataState
import com.bunbeauty.shared.presentation.menu.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val analyticService: AnalyticService,
) : SharedViewModel() {

    private val mutableMenuState = MutableStateFlow(
        MenuDataState(
            categoryItemList = emptyList(),
            cartCostAndCount = null,
            menuItemList = emptyList(),
            state = MenuDataState.State.Loading,
            userScrollEnabled = true,
            eventList = emptyList(),
        )
    )
    val menuState = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    init {
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
        sharedScope.launchSafe(
            block = {
                observeCartUseCase().collectLatest { cartTotalAndCount ->
                    mutableMenuState.update { state ->
                        state.copy(cartCostAndCount = cartTotalAndCount)
                    }
                }
            },
            onError = { throwable ->
                handleError(throwable)
            }
        )
    }

    fun getMenu() {
        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuDataState.State.Loading
            )
        }

        sharedScope.launchSafe(
            block = {
                val menuSectionList = menuProductInteractor.getMenuSectionList()
                if (selectedCategoryUuid == null) {
                    selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
                }

                val discountItem =
                    getDiscountUseCase()?.firstOrderDiscount?.toString()?.let { discount ->
                        MenuItem.Discount(discount = discount)
                    }
                val menuItemList = listOfNotNull(discountItem) +
                        menuSectionList.flatMap { menuSection ->
                            menuSection.toMenuItemList()
                        }
                mutableMenuState.update { oldState ->
                    oldState.copy(
                        categoryItemList = menuSectionList.map { menuSection ->
                            toCategoryItemModel(menuSection)
                        },
                        menuItemList = menuItemList,
                        state = MenuDataState.State.Success,
                    )
                }
            },
            onError = { throwable ->
                handleError(throwable)
            }
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

        sharedScope.launchSafe(
            block = {
                val menuItemModelList = mutableMenuState.value.menuItemList
                menuItemModelList.filterIsInstance<MenuItem.CategoryHeader>()
                    .findLast { menuItemModel ->
                        menuItemModelList.indexOf(menuItemModel) <= menuPosition
                    }?.let { menuItemModel ->
                        setCategory(menuItemModel.uuid)
                    }
            },
            onError = { throwable ->
                handleError(throwable)
            }

        )
    }

    private fun handleError(throwable: Throwable) {
        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuDataState.State.Error(throwable)
            )
        }
    }

    fun onMenuItemClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        mutableMenuState.update { oldState ->
            oldState + MenuDataState.Event.GoToSelectedItem(
                uuid = menuProduct.uuid,
                name = menuProduct.name
            )
        }
    }

    private fun findMenuProduct(uuid: String): MenuItem.Product? {
        return mutableMenuState.value.menuItemList
            .filterIsInstance<MenuItem.Product>()
            .find { menuItem ->
                (menuItem.uuid == uuid)
            }
    }

    fun onAddProductClicked(menuProductUuid: String) {
        val menuProduct = findMenuProduct(uuid = menuProductUuid) ?: return

        analyticService.sendEvent(
            event = AddMenuProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProduct.uuid)
            ),
        )
        sharedScope.launchSafe(
            block = {
                if (menuProduct.hasAdditions) {
                    mutableMenuState.update { oldState ->
                        oldState + MenuDataState.Event.GoToSelectedItem(
                            uuid = menuProduct.uuid,
                            name = menuProduct.name
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
            }
        )
    }

    fun getMenuListPosition(categoryItem: CategoryItem): Int {
        val index = mutableMenuState.value.menuItemList.indexOfFirst { menuItemModel ->
            (menuItemModel as? MenuItem.CategoryHeader)?.uuid == categoryItem.uuid
        }
        return if (index == 1 && mutableMenuState.value.hasDiscountItem) {
            0
        } else {
            index
        }
    }

    private fun setCategory(categoryUuid: String) {
        sharedScope.launch {
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
                    menuItemList = oldState.menuItemList
                )
            }
        }
    }

    private fun toCategoryItemModel(menuSection: MenuSection): CategoryItem {
        return CategoryItem(
            key = "CategoryItemModel ${menuSection.category.uuid}",
            uuid = menuSection.category.uuid,
            name = menuSection.category.name,
            isSelected = isCategorySelected(menuSection.category.uuid)
        )
    }

    private fun isCategorySelected(categoryUuid: String): Boolean {
        return selectedCategoryUuid?.let {
            selectedCategoryUuid == categoryUuid
        } ?: false
    }

    fun consumeEventList(eventList: List<MenuDataState.Event>) {
        mutableMenuState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
