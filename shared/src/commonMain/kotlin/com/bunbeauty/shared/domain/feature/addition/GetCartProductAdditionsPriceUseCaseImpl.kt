package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.addition.CartProductAddition

interface GetCartProductAdditionsPriceUseCase {
    operator fun invoke(additionList: List<CartProductAddition>): Int
}

class GetCartProductAdditionsPriceUseCaseImpl : GetCartProductAdditionsPriceUseCase {
    override operator fun invoke(additionList: List<CartProductAddition>) =
        additionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}
