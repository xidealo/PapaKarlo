package com.bunbeauty.shared.domain.feature.menuproduct

import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo

interface GetMenuProductListUseCase {
    suspend operator fun invoke(): List<MenuProduct>
}

class GetMenuProductListUseCaseImpl(
    private val menuProductRepo: MenuProductRepo
) : GetMenuProductListUseCase {

    override suspend operator fun invoke(): List<MenuProduct> {
        return menuProductRepo.getMenuProductList()
            .filter { menuProduct ->
                menuProduct.visible
            }
            .map { menuProduct ->
                menuProduct.copy(
                    additionGroups = menuProduct.additionGroups.filter { additionGroup ->
                        additionGroup.isVisible
                    }
                )
            }
    }
}
