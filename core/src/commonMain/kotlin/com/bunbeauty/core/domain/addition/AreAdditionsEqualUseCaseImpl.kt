package com.bunbeauty.core.domain.addition

import com.bunbeauty.core.model.cart.CartProduct

interface AreAdditionsEqualUseCase {
    operator fun invoke(
        cartProduct: CartProduct,
        additionUuidList: List<String>,
    ): Boolean
}

class AreAdditionsEqualUseCaseImpl : AreAdditionsEqualUseCase {
    override operator fun invoke(
        cartProduct: CartProduct,
        additionUuidList: List<String>,
    ): Boolean {
        if (cartProduct.additionList.size != additionUuidList.size) {
            return false
        }

        val sortedCartProductAdditionUuidList =
            cartProduct.additionList
                .map { addition ->
                    addition.additionUuid
                }.sorted()
        val sortedAdditionUuidList = additionUuidList.sorted()

        return sortedCartProductAdditionUuidList == sortedAdditionUuidList
    }
}
