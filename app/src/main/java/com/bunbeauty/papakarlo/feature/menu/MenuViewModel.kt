package com.bunbeauty.papakarlo.feature.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.menu.MenuSection
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.extensions.toStateSuccess
import com.bunbeauty.papakarlo.extensions.toStateWithErrorSuccess
import com.bunbeauty.papakarlo.feature.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.feature.menu.view_state.CategoryItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuProductItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuUI
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val stringUtil: IStringUtil,
) : CartViewModel() {

    private val mutableMenuState: MutableStateFlow<StateWithError<MenuUI>> =
        MutableStateFlow(StateWithError.Loading())
    val menuState: StateFlow<StateWithError<MenuUI>> = mutableMenuState.asStateFlow()

    private var selectedCategoryUuid: String? = null
    private var currentMenuPosition = 0

    var autoScrolling = false

    fun getMenu() {
        mutableMenuState.value = StateWithError.Loading()
        viewModelScope.launch {
            mutableMenuState.value =
                menuProductInteractor.getMenuSectionList()?.let { menuSectionList ->
                    if (selectedCategoryUuid == null) {
                        selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
                    }
                    toMenu(menuSectionList)
                }?.toStateWithErrorSuccess() ?: StateWithError.Error(
                    baseResourcesProvider.getString(R.string.error_menu_loading)
                )
        }
    }

    fun onCategoryClicked(categoryItemModel: CategoryItemModel) {
        setCategory(categoryItemModel.uuid)
    }

    fun onMenuPositionChanged(menuPosition: Int) {
        if (autoScrolling || menuPosition == currentMenuPosition) {
            return
        }
        currentMenuPosition = menuPosition

        viewModelScope.launch {
            val menuItemModelList =
                (mutableMenuState.value as? StateWithError.Success)?.data?.menuItemModelList
            menuItemModelList?.filterIsInstance(MenuItemModel.MenuCategoryHeaderItemModel::class.java)
                ?.findLast { menuItemModel ->
                    menuItemModelList.indexOf(menuItemModel) <= menuPosition
                }?.let { menuItemModel ->
                    setCategory(menuItemModel.uuid)
                }
        }
    }

    fun onMenuItemClicked(menuProductItemModel: MenuProductItemModel) {
        router.navigate(toProductFragment(menuProductItemModel.uuid, menuProductItemModel.name))
    }

    fun onAddProductClicked(menuProductUuid: String) {
        addProductToCart(menuProductUuid)
    }

    fun getMenuListPosition(categoryItemModel: CategoryItemModel): Int {
        return (mutableMenuState.value as StateWithError.Success).data.menuItemModelList.indexOfFirst { menuItemModel ->
            (menuItemModel as? MenuItemModel.MenuCategoryHeaderItemModel)?.uuid == categoryItemModel.uuid
        }
    }

    private fun setCategory(categoryUuid: String) {
        viewModelScope.launch {
            if (selectedCategoryUuid == categoryUuid) {
                return@launch
            }
            selectedCategoryUuid = categoryUuid

            val menu = (mutableMenuState.value as StateWithError.Success).data
            val categoryItemModelList = menu.categoryItemModelList.map { categoryItemModel ->
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
                categoryItemModelList = categoryItemModelList,
                menuItemModelList = menu.menuItemModelList
            ).toStateWithErrorSuccess()
        }
    }

    private fun observeMenu() {
        menuProductInteractor.observeMenuSectionList().launchOnEach { menuSectionList ->
            if (selectedCategoryUuid == null) {
                selectedCategoryUuid = menuSectionList.firstOrNull()?.category?.uuid
            }
            mutableMenuState.value = toMenu(menuSectionList).toStateWithErrorSuccess()
        }
    }

    private fun toMenu(menuSectionList: List<MenuSection>): MenuUI {
        return MenuUI(
            categoryItemModelList = menuSectionList.map { menuSection ->
                toCategoryItemModel(menuSection)
            },
            menuItemModelList = menuSectionList.flatMap { menuSection ->
                listOf(toMenuCategoryItemModel(menuSection)) +
                        toMenuProductItemModelList(menuSection)
            }
        )
    }

    private fun toCategoryItemModel(menuSection: MenuSection): CategoryItemModel {
        return CategoryItemModel(
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

    private fun toMenuCategoryItemModel(menuSection: MenuSection): MenuItemModel.MenuCategoryHeaderItemModel {
        return MenuItemModel.MenuCategoryHeaderItemModel(
            key = "MenuCategoryHeaderItemModel ${menuSection.category.uuid}",
            uuid = menuSection.category.uuid,
            name = menuSection.category.name
        )
    }

    private fun toMenuProductItemModelList(menuSection: MenuSection): List<MenuItemModel.MenuProductPairItemModel> {
        fun toMenuProductItemModel(menuProduct: MenuProduct): MenuProductItemModel {
            return MenuProductItemModel(
                uuid = menuProduct.uuid,
                photoLink = menuProduct.photoLink,
                name = menuProduct.name,
                oldPrice = menuProduct.oldPrice?.let { price ->
                    stringUtil.getCostString(price)
                },
                newPrice = stringUtil.getCostString(menuProduct.newPrice)
            )
        }

        fun toMenuProductItemModel(menuProduct: MenuProduct?): MenuProductItemModel? {
            return menuProduct?.let {
                toMenuProductItemModel(it)
            }
        }

        return menuSection.menuProductList.chunked(2) { menuProductChunk ->
            val firstMenuProduct = menuProductChunk[0]
            val secondMenuProduct = menuProductChunk.getOrNull(1)
            MenuItemModel.MenuProductPairItemModel(
                key = "MenuProductPairItemModel ${firstMenuProduct.uuid} ${secondMenuProduct?.uuid} ${menuSection.category}",
                firstProduct = toMenuProductItemModel(menuProductChunk[0]),
                secondProduct = toMenuProductItemModel(menuProductChunk.getOrNull(1))
            )
        }
    }
}