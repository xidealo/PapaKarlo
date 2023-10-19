package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct

class GetNewTotalCostUseCase(private val getDiscountUseCase: GetDiscountUseCase) {
    suspend operator fun invoke(productList: List<CartProduct>): Int {
        val newTotalCost = productList.sumOf { orderProductEntity ->
            orderProductEntity.count * orderProductEntity.product.newPrice
        }
        val discount =
            (newTotalCost * (getDiscountUseCase()?.firstOrderDiscount ?: 0) / 100.0).toInt()
        return newTotalCost - discount
    }
}