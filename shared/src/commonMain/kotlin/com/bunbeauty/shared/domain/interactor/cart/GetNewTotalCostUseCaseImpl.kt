package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct

interface GetNewTotalCostUseCase {
    suspend operator fun invoke(cartProductList: List<CartProduct>): Int
}

class GetNewTotalCostUseCaseImpl(
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getCartProductAdditionsPriceUseCase: GetCartProductAdditionsPriceUseCase
) : GetNewTotalCostUseCase {
    override suspend operator fun invoke(cartProductList: List<CartProduct>): Int {
        val newTotalCost = cartProductList.sumOf { cartProduct ->
            val sumOfNewPriceAndAdditions =
                cartProduct.product.newPrice + getCartProductAdditionsPriceUseCase(additionList = cartProduct.additionList)
            sumOfNewPriceAndAdditions * cartProduct.count
        }
        val discount =
            (newTotalCost * (getDiscountUseCase()?.firstOrderDiscount ?: 0) / 100.0).toInt()
        return newTotalCost - discount
    }
}
