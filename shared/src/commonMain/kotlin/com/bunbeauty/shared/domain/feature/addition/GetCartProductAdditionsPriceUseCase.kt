package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.addition.CartProductAddition

class GetCartProductAdditionsPriceUseCase {
    operator fun invoke(additionList: List<CartProductAddition>) =
        additionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}