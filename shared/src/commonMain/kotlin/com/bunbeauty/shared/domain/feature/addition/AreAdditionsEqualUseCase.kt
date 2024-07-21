package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.cart.CartProduct

class AreAdditionsEqualUseCase {

    operator fun invoke(
        cartProduct: CartProduct,
        additionUuidList: List<String>
    ): Boolean {
        if (cartProduct.additionList.size != additionUuidList.size) {
            return false
        }

        val sortedCartProductAdditionUuidList = cartProduct.additionList.map { addition ->
            addition.additionUuid
        }.sorted()
        val sortedAdditionUuidList = additionUuidList.sorted()

        return sortedCartProductAdditionUuidList == sortedAdditionUuidList
    }
}
