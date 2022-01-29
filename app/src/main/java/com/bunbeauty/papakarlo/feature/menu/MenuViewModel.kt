package com.bunbeauty.papakarlo.feature.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.categories.ICategoryInteractor
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.feature.menu.category.CategoryItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val mutableMenuPosition: MutableSharedFlow<Int> = MutableSharedFlow(replay = 0)
    val menuPosition: SharedFlow<Int> = mutableMenuPosition.asSharedFlow()

    init {
        observeCategoryList()
        observeMenuList()
    }

    fun onCategorySelected(categoryUuid: String) {
        setCategory(categoryUuid)
        viewModelScope.launch {
            val menuPosition =
                menuProductInteractor.getCurrentMenuPosition(categoryUuid, menuModelList)
            mutableMenuPosition.emit(menuPosition)
        }
    }

    fun setCategory(categoryUuid: String) {
        if (selectedCategoryUuid == categoryUuid) {
            return
        }
        selectedCategoryUuid = categoryUuid
        categoryItemList = categoryItemList.mapIndexed { index, categoryItem ->
            if (categoryUuid == categoryItem.uuid) {
                mutableCategoryPosition.value = index
            }
            CategoryItem(
                uuid = categoryItem.uuid,
                name = categoryItem.name,
                isSelected = (categoryUuid == categoryItem.uuid)
            )
        }
        mutableCategoryList.value = categoryItemList
    }

    fun onMenuPositionChanged(menuPosition: Int) {
        categoryInteractor.getCurrentCategory(menuPosition, menuModelList)?.let { section ->
            setCategory(section.category.uuid)
        }
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
            val selectedCategoryUuid = categoryList.firstOrNull()?.uuid
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