package com.bunbeauty.papakarlo.presentation.menu

import com.bunbeauty.domain.interactor.categories.ICategoryInteractor
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.fragment.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.presentation.item.CategoryItem
import com.bunbeauty.presentation.item.MenuItem
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val categoryInteractor: ICategoryInteractor,
    private val menuProductInteractor: IMenuProductInteractor,
    private val stringUtil: IStringUtil,
) : CartViewModel() {

    private var selectedCategoryUuid: String? = null

    private var categoryItemList: List<CategoryItem> = emptyList()
    private val mutableCategoryList: MutableStateFlow<List<CategoryItem>> =
        MutableStateFlow(categoryItemList)
    val categoryList: StateFlow<List<CategoryItem>> = mutableCategoryList.asStateFlow()

    private val mutableCategoryPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val categoryPosition: StateFlow<Int> = mutableCategoryPosition.asStateFlow()

    private var menuModelList: List<MenuModel> = emptyList()
    private val mutableMenuList: MutableStateFlow<List<MenuItem>> =
        MutableStateFlow(emptyList())
    val menuList: StateFlow<List<MenuItem>> = mutableMenuList.asStateFlow()

    init {
        observeCategoryList()
        observeMenuList()
    }

    fun onCategorySelected(categoryItemUuid: String) {
        selectedCategoryUuid = categoryItemUuid
        categoryItemList = categoryItemList.mapIndexed { index, categoryItem ->
            if (selectedCategoryUuid == categoryItem.uuid) {
                mutableCategoryPosition.value = index
            }
            CategoryItem(
                uuid = categoryItem.uuid,
                name = categoryItem.name,
                isSelected = (selectedCategoryUuid == categoryItem.uuid)
            )
        }
        mutableCategoryList.value = categoryItemList
    }

    fun checkSelectedCategory(menuPosition: Int) {
        categoryInteractor.getCurrentCategory(menuPosition, menuModelList)?.let { section ->
            onCategorySelected(section.category.uuid)
        }
    }

    fun getMenuPosition(currentCategoryItem: CategoryItem): Int {
        return menuProductInteractor.getCurrentMenuPosition(currentCategoryItem.uuid, menuModelList)
    }

    fun onMenuItemClicked(menuItem: MenuItem) {
        if (menuItem is MenuItem.MenuProductItem) {
            router.navigate(toProductFragment(menuItem.uuid, menuItem.name, menuItem.photoLink))
        }
    }

    fun onAddProductClicked(menuProductItem: MenuItem.MenuProductItem) {
        addProductToCart(menuProductItem.uuid)
    }

    private fun observeCategoryList() {
        categoryInteractor.observeCategoryList().launchOnEach { categoryList ->
            if (selectedCategoryUuid == null && categoryList.isNotEmpty()) {
                selectedCategoryUuid = categoryList.first().uuid
            }
            categoryItemList = categoryList.map { category ->
                CategoryItem(
                    uuid = category.uuid,
                    name = category.name,
                    isSelected = (selectedCategoryUuid == category.uuid)
                )
            }
            mutableCategoryList.value = categoryItemList
        }
    }

    private fun observeMenuList() {
        menuProductInteractor.observeMenuList().launchOnEach { menuList ->
            menuModelList = menuList
            mutableMenuList.value = menuList.map { menuModel ->
                when (menuModel) {
                    is MenuModel.Section -> {
                        MenuItem.CategorySectionItem(
                            uuid = menuModel.category.uuid,
                            name = menuModel.category.name,
                        )
                    }
                    is MenuModel.Product -> {
                        MenuItem.MenuProductItem(
                            uuid = menuModel.menuProduct.uuid,
                            name = menuModel.menuProduct.name,
                            newPrice = stringUtil.getCostString(menuModel.menuProduct.newPrice),
                            oldPrice = stringUtil.getCostString(menuModel.menuProduct.oldPrice),
                            photoLink = menuModel.menuProduct.photoLink,
                        )
                    }
                }
            }
        }
    }

}