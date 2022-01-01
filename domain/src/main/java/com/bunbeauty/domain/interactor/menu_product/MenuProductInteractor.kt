package com.bunbeauty.domain.interactor.menu_product

import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MenuProductInteractor @Inject constructor(
    @Api private val menuProductRepo: MenuProductRepo,
) : IMenuProductInteractor {

    override fun observeMenuList(): Flow<List<MenuModel>> {
        return menuProductRepo.observeMenuProductList().map { menuProductList ->
            val menuList = mutableListOf<MenuModel>()
            menuProductList.flatMap { menuProduct ->
                menuProduct.categoryList
            }.toSet()
                .sortedBy { category ->
                    category.priority
                }
                .forEach { category ->
                    menuList.add(MenuModel.Section(category))
                    val categoryMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.categoryList.contains(category)
                    }.sortedWith(compareBy(MenuProduct::newPrice, MenuProduct::name))
                        .reversed()
                        .map { menuProduct ->
                            MenuModel.Product(menuProduct)
                        }
                    menuList.addAll(categoryMenuProductList)
                }
            menuList
        }
    }

    override fun getCurrentMenuPosition(
        currentCategoryUuid: String,
        menuList: List<MenuModel>
    ): Int {
        val menuItem = menuList.find { menuItem ->
            (menuItem is MenuModel.Section) && (menuItem.category.uuid == currentCategoryUuid)
        }
        val position = menuList.indexOf(menuItem)
        return if (position == -1) {
            0
        } else {
            position
        }
    }
}