package com.bunbeauty.shared.domain.feature.menuproduct

import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo

class GetMenuProductUseCase(
    private val menuProductRepo: MenuProductRepo
) {

    suspend operator fun invoke(menuProductUuid: String): MenuProduct? {
        val menuProduct = menuProductRepo.getMenuProductByUuid(menuProductUuid)

        return menuProduct?.copy(
            additionGroups = getAdditionGroupList(menuProduct)
        )
    }

    private fun getAdditionGroupList(menuProduct: MenuProduct) =
        menuProduct.additionGroups.map { additionGroup ->
            additionGroup.copy(
                additionList = getAdditionList(additionGroup)
            )
        }.filter { additionGroup ->
            additionGroup.isVisible
        }.sortedBy { additionGroup ->
            additionGroup.priority
        }

    private fun getAdditionList(additionGroup: AdditionGroup) =
        additionGroup.additionList.filter { addition ->
            addition.isVisible
        }.sortedBy { addition ->
            addition.priority
        }
}
