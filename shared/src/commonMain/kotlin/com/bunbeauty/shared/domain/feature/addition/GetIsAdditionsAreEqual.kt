package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.cart.CartProduct

//TODO tests
class GetIsAdditionsAreEqual {
    operator fun invoke(
        initialCartProduct: CartProduct?,
        additionUuidList: List<String>,
    ): Boolean {
        // Check if the lists have the same size
        val listsHaveSameSize =
            initialCartProduct?.cartProductAdditionList?.size == additionUuidList.size

        // Check if each element in 'additionList' has a corresponding element in 'cartProductAdditionList' with the same 'uuid'
        val listsAreEqual = listsHaveSameSize && additionUuidList.all { additionUuid ->
            initialCartProduct?.cartProductAdditionList?.any { cartProductAddition ->
                cartProductAddition.additionUuid == additionUuid
            } ?: false
        }
        return listsAreEqual
    }
}