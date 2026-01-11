package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.core.domain.GetCartProductAdditionsPriceUseCase

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
