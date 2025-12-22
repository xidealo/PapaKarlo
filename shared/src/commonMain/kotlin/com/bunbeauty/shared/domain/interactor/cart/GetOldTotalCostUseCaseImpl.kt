package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct

interface GetOldTotalCostUseCase {
    operator fun invoke(cartProductList: List<CartProduct>): Int
}

class GetOldTotalCostUseCaseImpl(
    private val getCartProductAdditionsPriceUseCase: GetCartProductAdditionsPriceUseCase,
) : GetOldTotalCostUseCase {
    override operator fun invoke(cartProductList: List<CartProduct>): Int {
        val oldTotalCost =
            cartProductList.sumOf { cartProduct ->
                val price = (cartProduct.product.oldPrice ?: cartProduct.product.newPrice)
                val sumOfCostAndAdditions =
                    price + getCartProductAdditionsPriceUseCase(cartProduct.additionList)
                sumOfCostAndAdditions * cartProduct.count
            }

        return oldTotalCost
    }

    private fun getAdditionsPrice(cartProduct: CartProduct) =
        cartProduct.additionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}
