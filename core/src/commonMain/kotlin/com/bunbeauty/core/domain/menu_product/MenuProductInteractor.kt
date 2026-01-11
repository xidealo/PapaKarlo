package com.bunbeauty.core.domain.menu_product

import com.bunbeauty.core.domain.repo.MenuProductRepo
import com.bunbeauty.core.model.menu.MenuSection
import com.bunbeauty.core.model.product.MenuProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MenuProductInteractor(
    private val menuProductRepo: MenuProductRepo,
    private val getMenuProductListUseCase: GetMenuProductListUseCase,
) : IMenuProductInteractor {
    override suspend fun getMenuSectionList(): List<MenuSection> =
        withContext(Dispatchers.Default) {
            getMenuProductListUseCase().let(::toMenuSectionList)
        }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> =
        menuProductRepo.observeMenuProductByUuid(menuProductUuid)

    private fun toMenuSectionList(menuProductList: List<MenuProduct>): List<MenuSection> =
        menuProductList
            .flatMap { menuProduct ->
                menuProduct.categoryList.map { category ->
                    category to menuProduct
                }
            }.groupBy { (category, _) ->
                category
            }.map { (category, categoryWithMenuProductList) ->
                MenuSection(
                    category = category,
                    menuProductList =
                        categoryWithMenuProductList
                            .map { (_, menuProduct) ->
                                menuProduct
                            }.sortedWith(
                                compareBy(
                                    { comparableMenuProduct ->
                                        categoryWithMenuProductList
                                            .filter { (_, menuProduct) ->
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
                                    MenuProduct::name,
                                ),
                            ),
                )
            }.sortedBy { menuSection ->
                menuSection.category.priority
            }
}
