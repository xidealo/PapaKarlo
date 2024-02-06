package com.bunbeauty.shared.domain.interactor.menu_product

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MenuProductInteractor(
    private val menuProductRepo: MenuProductRepo,
) : IMenuProductInteractor {

    override suspend fun getMenuSectionList(): List<MenuSection> {
        return withContext(Dispatchers.Default) {
            menuProductRepo.getMenuProductList()
                .filter { it.visible }
                .let { menuProductList ->
                    if (menuProductList.isEmpty()) {
                        null
                    } else {
                        toMenuSectionList(menuProductList)
                    }
                } ?: emptyList()

        }
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductRepo.observeMenuProductByUuid(menuProductUuid)
    }

    override fun observeMenuProductByUuidForSwift(menuProductUuid: String): CommonFlow<MenuProduct?> {
        return menuProductRepo.observeMenuProductByUuid(menuProductUuid).asCommonFlow()
    }

    override suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct? {
        return menuProductRepo.getMenuProductByUuid(menuProductUuid = menuProductUuid)
    }

    private fun toMenuSectionList(menuProductList: List<MenuProduct>): List<MenuSection> {
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
                ).map { menuProduct ->
                    //TODO(only for test!)
                    if (menuProduct.name == "Шаурма Армянская") {
                        menuProduct.copy(
                            additionGroups = listOf(
                                AdditionGroup(
                                    additionList = listOf(
                                        Addition(
                                            isSelected = true,
                                            isVisible = true,
                                            name = "Potato",
                                            fullName = "kartoxa 2x",
                                            photoLink = "",
                                            price = null,
                                            uuid = "uuid1",
                                            additionGroupUuid = "sss",
                                            priority = 1,
                                        ),
                                        Addition(
                                            isSelected = true,
                                            isVisible = true,
                                            name = "Bacon",
                                            fullName = "Bacon jopa",
                                            photoLink = "",
                                            price = 2,
                                            uuid = "uuid2",
                                            additionGroupUuid = "sss",
                                            priority = 1,
                                        )
                                    ),
                                    isVisible = true,
                                    name = "jopa",
                                    singleChoice = true,
                                    uuid = "sss",
                                    priority = 1,
                                )
                            )
                        )
                    } else {
                        menuProduct
                    }

                }
            )
        }.sortedBy { menuSection ->
            menuSection.category.priority
        }
    }
}