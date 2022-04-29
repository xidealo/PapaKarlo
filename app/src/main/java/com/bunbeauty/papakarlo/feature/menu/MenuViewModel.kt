package com.bunbeauty.papakarlo.feature.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.menu.MenuSection
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuProductItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuUI
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val stringUtil: IStringUtil,
) : CartViewModel() {

    private val mutableMenuState: MutableStateFlow<State<MenuUI>> =
        MutableStateFlow(State.Loading())
    val menuState: StateFlow<State<MenuUI>> = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    var autoScrolling = false

    fun getMenu() {
        mutableMenuState.value = State.Loading()
        viewModelScope.launch {
            mutableMenuState.value =
                menuProductInteractor.getMenuSectionList()?.let { menuSectionList ->
                    if (selectedCategoryUuid == null) {
                        selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
                    }
                    toMenu(menuSectionList)
                }.toState(resourcesProvider.getString(R.string.error_menu_loading))
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

        viewModelScope.launch {
            val menuItemModelList =
                (mutableMenuState.value as? State.Success)?.data?.menuItemList
            menuItemModelList?.filterIsInstance(MenuItem.MenuCategoryHeaderItem::class.java)
                ?.findLast { menuItemModel ->
                    menuItemModelList.indexOf(menuItemModel) <= menuPosition
                }?.let { menuItemModel ->
                    setCategory(menuItemModel.uuid)
                }
        }
    }

    fun onMenuItemClicked(menuProductItem: MenuProductItem) {
        router.navigate(toProductFragment(menuProductItem.uuid, menuProductItem.name))
    }

    fun onAddProductClicked(menuProductUuid: String) {
        addProductToCart(menuProductUuid)
    }

    fun getMenuListPosition(categoryItem: CategoryItem): Int {
        return (mutableMenuState.value as State.Success).data.menuItemList.indexOfFirst { menuItemModel ->
            (menuItemModel as? MenuItem.MenuCategoryHeaderItem)?.uuid == categoryItem.uuid
        }
    }

    private fun setCategory(categoryUuid: String) {
        viewModelScope.launch {
            if (selectedCategoryUuid == categoryUuid) {
                return@launch
            }
            selectedCategoryUuid = categoryUuid

            val menu = (mutableMenuState.value as State.Success).data
            val categoryItemModelList = menu.categoryItemList.map { categoryItemModel ->
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
            mutableMenuState.value = menu.copy(
                categoryItemList = categoryItemModelList,
                menuItemList = menu.menuItemList
            ).toState()
        }
    }

    private fun toMenu(menuSectionList: List<MenuSection>): MenuUI {
        return MenuUI(
            categoryItemList = menuSectionList.map { menuSection ->
                toCategoryItemModel(menuSection)
            },
            menuItemList = menuSectionList.flatMap { menuSection ->
                listOf(toMenuCategoryItemModel(menuSection)) +
                        toMenuProductItemModelList(menuSection)
            }
        )
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
}