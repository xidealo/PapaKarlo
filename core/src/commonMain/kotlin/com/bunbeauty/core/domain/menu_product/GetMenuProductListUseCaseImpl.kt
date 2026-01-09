package com.bunbeauty.core.domain.menu_product

import com.bunbeauty.core.model.product.MenuProduct
import com.bunbeauty.core.domain.repo.MenuProductRepo

interface GetMenuProductListUseCase {
    suspend operator fun invoke(): List<MenuProduct>
}

class GetMenuProductListUseCaseImpl(
    private val menuProductRepo: MenuProductRepo,
) : GetMenuProductListUseCase {
    override suspend operator fun invoke(): List<MenuProduct> =
        menuProductRepo
            .getMenuProductList()
            .filter { menuProduct ->
                menuProduct.visible
            }.map { menuProduct ->
                menuProduct.copy(
                    additionGroups =
                        menuProduct.additionGroups.filter { additionGroup ->
                            additionGroup.isVisible
                        },
                )
            }
}
