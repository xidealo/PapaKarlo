package com.bunbeauty.core.domain

import com.bunbeauty.core.model.addition.CartProductAddition

interface GetCartProductAdditionsPriceUseCase {
    operator fun invoke(additionList: List<CartProductAddition>): Int
}

class GetCartProductAdditionsPriceUseCaseImpl : GetCartProductAdditionsPriceUseCase {
    override operator fun invoke(additionList: List<CartProductAddition>) =
        additionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}
