package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.model.cart.CartProduct

class GetOldTotalCostUseCase {
    operator fun invoke(cartProductList: List<CartProduct>): Int {
        val oldTotalCost = cartProductList.sumOf { cartProduct ->
            val sumOfCostAndAdditions =
                (cartProduct.product.oldPrice ?: cartProduct.product.newPrice) + getAdditionsPrice(
                    cartProduct
                )
            sumOfCostAndAdditions * cartProduct.count
        }

        return oldTotalCost
    }

    private fun getAdditionsPrice(cartProduct: CartProduct) =
        cartProduct.cartProductAdditionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}