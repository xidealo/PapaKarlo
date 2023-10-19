package com.bunbeauty.shared.presentation.menu

import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
) : SharedViewModel() {

    private val mutableMenuState = MutableStateFlow(
        MenuState(
            categoryItemList = emptyList(),
            cartCostAndCount = null,
            menuItemList = emptyList(),
            state = MenuState.State.Loading,
            userScrollEnabled = true,
            eventList = emptyList(),
        )
    )
    val menuState = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuState.State.Error(throwable)
            )
        }
    }

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
        sharedScope.launch(exceptionHandler) {
            observeCartUseCase().collectLatest { cartTotalAndCount ->
                mutableMenuState.update { state ->
                    state.copy(cartCostAndCount = cartTotalAndCount)
                }
            }
        }
    }

    fun getMenu() {
        mutableMenuState.update { oldState ->
            oldState.copy(
                state = MenuState.State.Loading
            )
        }

        sharedScope.launch(exceptionHandler) {
            val menuSectionList = menuProductInteractor.getMenuSectionList()
            if (selectedCategoryUuid == null) {
                selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
            }

            val discountItem =
                getDiscountUseCase()?.firstOrderDiscount?.toString()?.let { discount ->
                    MenuItem.DiscountItem(
                        key = "MenuProductDiscountModel $discount",
                        discount = discount
                    )
                }

            val menuItemList = listOfNotNull(discountItem) + menuSectionList.flatMap { menuSection ->
                listOf(toMenuCategoryItemModel(menuSection)) +
                    toMenuProductItemModelList(menuSection)
            }

            mutableMenuState.update { oldState ->
                oldState.copy(
                    categoryItemList = menuSectionList.map { menuSection ->
                        toCategoryItemModel(menuSection)
                    },
                    menuItemList = menuItemList,
                    state = MenuState.State.Success,
                )
            }
        }
    }

    fun onCategoryClicked(categoryItem: CategoryItem) {
        setCategory(categoryItem.uuid)
    }

    fun onMenuPositionChanged(menuPosition: Int) {
        if (!mutableMenuState.value.userScrollEnabled || menuPosition == currentMenuPosition) {
            return
        }

        currentMenuPosition = menuPosition

        sharedScope.launch(exceptionHandler) {
            val menuItemModelList = mutableMenuState.value.menuItemList
            menuItemModelList.filterIsInstance<MenuItem.MenuCategoryHeaderItem>()
                .findLast { menuItemModel ->
                    menuItemModelList.indexOf(menuItemModel) <= menuPosition
                }?.let { menuItemModel ->
                    setCategory(menuItemModel.uuid)
                }
        }
    }

    fun onMenuItemClicked(menuProductItem: MenuProductItem) {
        mutableMenuState.update { oldState ->
            oldState + MenuState.Event.GoToSelectedItem(
                uuid = menuProductItem.uuid,
                name = menuProductItem.name
            )
        }
    }

    fun onAddProductClicked(menuProductUuid: String) {
        sharedScope.launch {
            addCartProductUseCase(menuProductUuid)
        }
    }

    fun getMenuListPosition(categoryItem: CategoryItem): Int {
        val index = mutableMenuState.value.menuItemList.indexOfFirst { menuItemModel ->
            (menuItemModel as? MenuItem.MenuCategoryHeaderItem)?.uuid == categoryItem.uuid
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

    private fun toMenuCategoryItemModel(menuSection: MenuSection): MenuItem.MenuCategoryHeaderItem {
        return MenuItem.MenuCategoryHeaderItem(
            key = "MenuCategoryHeaderItemModel ${menuSection.category.uuid}",
            uuid = menuSection.category.uuid,
            name = menuSection.category.name
        )
    }

    private fun toMenuProductItemModelList(menuSection: MenuSection): List<MenuItem.MenuProductListItem> {
        fun toMenuProductItemModel(menuProduct: MenuProduct): MenuProductItem {
            return MenuProductItem(
                uuid = menuProduct.uuid,
                photoLink = menuProduct.photoLink,
                name = menuProduct.name,
                oldPrice = menuProduct.oldPrice,
                newPrice = menuProduct.newPrice
            )
        }

        return menuSection.menuProductList.map { menuProduct ->
            MenuItem.MenuProductListItem(
                key = "MenuProductPairItemModel ${menuProduct.uuid} ${menuSection.category}",
                product = toMenuProductItemModel(menuProduct),
            )
        }
    }

    fun consumeEventList(eventList: List<MenuState.Event>) {
        mutableMenuState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
