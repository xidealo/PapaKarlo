package com.bunbeauty.papakarlo.feature.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuProductItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuState
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.domain.model.product.MenuProduct
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val stringUtil: IStringUtil,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase
) : BaseViewModel() {

    private val mutableMenuState = MutableStateFlow(MenuState())
    val menuState = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    var autoScrolling = false

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

    private fun observeCart() {
        viewModelScope.launch(exceptionHandler) {
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

        viewModelScope.launch(exceptionHandler) {
            val menuSectionList = menuProductInteractor.getMenuSectionList()
            if (selectedCategoryUuid == null) {
                selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
            }

            mutableMenuState.update { oldState ->
                oldState.copy(
                    categoryItemList = menuSectionList.map { menuSection ->
                        toCategoryItemModel(menuSection)
                    },
                    menuItemList = menuSectionList.flatMap { menuSection ->
                        listOf(toMenuCategoryItemModel(menuSection)) +
                            toMenuProductItemModelList(menuSection)
                    },
                    state = MenuState.State.Success
                )
            }
        }
    }

    fun onCategoryClicked(categoryItem: CategoryItem) {
        setCategory(categoryItem.uuid)
    }

    fun onMenuPositionChanged(menuPosition: Int) {
        if (autoScrolling || menuPosition == currentMenuPosition) {
            return
        }
        currentMenuPosition = menuPosition

        viewModelScope.launch(exceptionHandler) {
            val menuItemModelList =
                mutableMenuState.value.menuItemList
            menuItemModelList.filterIsInstance(MenuItem.MenuCategoryHeaderItem::class.java)
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
        viewModelScope.launch {
            addCartProductUseCase(menuProductUuid)
        }
    }

    fun getMenuListPosition(categoryItem: CategoryItem): Int {
        return mutableMenuState.value.menuItemList.indexOfFirst { menuItemModel ->
            (menuItemModel as? MenuItem.MenuCategoryHeaderItem)?.uuid == categoryItem.uuid
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

    private fun toMenuProductItemModelList(menuSection: MenuSection): List<MenuItem.MenuProductPairItem> {
        fun toMenuProductItemModel(menuProduct: MenuProduct): MenuProductItem {
            return MenuProductItem(
                uuid = menuProduct.uuid,
                photoLink = menuProduct.photoLink,
                name = menuProduct.name,
                oldPrice = menuProduct.oldPrice?.let { price ->
                    stringUtil.getCostString(price)
                },
                newPrice = stringUtil.getCostString(menuProduct.newPrice)
            )
        }

        fun toMenuProductItemModel(menuProduct: MenuProduct?): MenuProductItem? {
            return menuProduct?.let {
                toMenuProductItemModel(it)
            }
        }

        return menuSection.menuProductList.chunked(2) { menuProductChunk ->
            val firstMenuProduct = menuProductChunk[0]
            val secondMenuProduct = menuProductChunk.getOrNull(1)
            MenuItem.MenuProductPairItem(
                key = "MenuProductPairItemModel ${firstMenuProduct.uuid} ${secondMenuProduct?.uuid} ${menuSection.category}",
                firstProduct = toMenuProductItemModel(menuProductChunk[0]),
                secondProduct = toMenuProductItemModel(menuProductChunk.getOrNull(1))
            )
        }
    }

    fun consumeEventList(eventList: List<MenuState.Event>) {
        mutableMenuState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
