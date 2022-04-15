package com.bunbeauty.domain.interactor.menu_product

import com.bunbeauty.domain.model.menu.MenuSection
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuProductInteractor(
    private val menuProductRepo: MenuProductRepo,
) : IMenuProductInteractor {

    override suspend fun getMenuSectionList(): List<MenuSection>? {
        return menuProductRepo.getMenuProductList().let { menuProductList ->
            if (menuProductList.isEmpty()) {
                null
            } else {
                toMenuSectionList(menuProductList)
            }
        }
    }

    override fun observeMenuSectionList(): Flow<List<MenuSection>> {
        return menuProductRepo.observeMenuProductList().map(::toMenuSectionList)
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductRepo.observeMenuProductByUuid(menuProductUuid)
    }

    fun toMenuSectionList(menuProductList: List<MenuProduct>): List<MenuSection> {
        return menuProductList.flatMap { menuProduct ->
            menuProduct.categoryList.map { category ->
                category to menuProduct
            }
        }.groupBy { (category, _) ->
            category
        }.map { (category, categoryWithMenuProductList) ->
            MenuSection(
                category = category,
                menuProductList = categoryWithMenuProductList.map { (_, menuProduct) ->
                    menuProduct
                }.sortedWith(
                    compareBy(
                        { comparableMenuProduct ->
                            categoryWithMenuProductList.filter { (_, menuProduct) ->
                                comparableMenuProduct.name.split(" ")[0] ==
                                        menuProduct.name.split(" ")[0]
                            }.maxOf { (_, menuProduct) ->
                                menuProduct.newPrice
                            }.let { price ->
                                1.0 / price
                            }
                        },
                        { comparableMenuProduct ->
                            comparableMenuProduct.name.split(" ")[0]
                        },
                        { comparableMenuProduct ->
                            1.0 / comparableMenuProduct.newPrice
                        },
                        MenuProduct::name
                    )
                )
            )
        }.sortedBy { menuSection ->
            menuSection.category.priority
        }
    }
}