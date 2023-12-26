package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct

class GetNewTotalCostUseCase(private val getDiscountUseCase: GetDiscountUseCase) {
    suspend operator fun invoke(cartProductList: List<CartProduct>): Int {
        val newTotalCost = cartProductList.sumOf { cartProduct ->
            (cartProduct.product.newPrice + getAdditionsPrice(cartProduct = cartProduct)) * cartProduct.count
        }
        val discount =
            (newTotalCost * (getDiscountUseCase()?.firstOrderDiscount ?: 0) / 100.0).toInt()
        return newTotalCost - discount
    }

    private fun getAdditionsPrice(cartProduct: CartProduct) =
        cartProduct.cartProductAdditionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}