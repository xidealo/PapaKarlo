package com.bunbeauty.shared.domain.feature.menu_product

import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo

class GetMenuProductUseCase(
    private val menuProductRepo: MenuProductRepo,
) {

    suspend operator fun invoke(menuProductUuid: String): MenuProduct? {
        val menuProduct = menuProductRepo.getMenuProductByUuid(menuProductUuid)

        return menuProduct?.copy(
            additionGroups = menuProduct.additionGroups.map { additionGroup ->
                additionGroup.copy(
                    additionList = additionGroup.additionList.sortedBy { addition ->
                        addition.priority
                    }
                )
            }.sortedBy { additionGroup ->
                additionGroup.priority
            }
        )
    }
}