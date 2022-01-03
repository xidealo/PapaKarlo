package com.bunbeauty.papakarlo.presentation.menu

import com.bunbeauty.domain.interactor.categories.ICategoryInteractor
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
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

    private val mutableCategoryList: MutableStateFlow<List<CategoryItem>> =
        MutableStateFlow(emptyList())
    val categoryList: StateFlow<List<CategoryItem>> = mutableCategoryList.asStateFlow()

    private val mutableMenuList: MutableStateFlow<List<MenuItem>> =
        MutableStateFlow(emptyList())
    val menuList: StateFlow<List<MenuItem>> = mutableMenuList.asStateFlow()

    init {
        observeCategoryList()
        observeMenuList()
    }

    private fun observeCategoryList() {
        categoryInteractor.observeCategoryList().launchOnEach { categoryList ->
            mutableCategoryList.value = categoryList.map { category ->
                CategoryItem(
                    uuid = category.uuid,
                    name = category.name,
                )
            }
        }
    }

    private fun observeMenuList() {
        menuProductInteractor.observeMenuList().launchOnEach { menuList ->
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