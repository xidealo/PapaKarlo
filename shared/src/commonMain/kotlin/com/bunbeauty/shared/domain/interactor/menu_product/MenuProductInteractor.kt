package com.bunbeauty.shared.domain.interactor.menu_product

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.feature.menuproduct.GetMenuProductListUseCase
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuProductInteractor(
    private val menuProductRepo: MenuProductRepo,
    private val getMenuProductListUseCase: GetMenuProductListUseCase,
) : IMenuProductInteractor {
    override suspend fun getMenuSectionList(): List<MenuSection> =
        withContext(Dispatchers.Default) {
            getMenuProductListUseCase().let(::toMenuSectionList)
        }

    override fun observeMenuProductByUuid(menuProductUuid: String): CommonFlow<MenuProduct?> =
        menuProductRepo.observeMenuProductByUuid(menuProductUuid).asCommonFlow()

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
