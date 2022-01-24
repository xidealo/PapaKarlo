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
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.categoryList.contains(category)
                    }
                    val categoryMenuProductList =
                        filteredMenuProductList.sortedWith(
                            compareBy(
                                { comparableMenuProduct ->
                                    val price = filteredMenuProductList.filter { menuProduct ->
                                        comparableMenuProduct.name.split(" ")[0] ==
                                                menuProduct.name.split(" ")[0]
                                    }.maxOf { menuProduct ->
                                        menuProduct.newPrice
                                    }
                                    1.0 / price
                                },
                                { comparableMenuProduct ->
                                    1.0 / comparableMenuProduct.newPrice
                                },
                                MenuProduct::name
                            )
                        ).map { menuProduct ->
                            MenuModel.Product(menuProduct)
                        }
                    menuList.addAll(categoryMenuProductList)
                }
            menuList
        }
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductRepo.observeMenuProductByUuid(menuProductUuid)
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