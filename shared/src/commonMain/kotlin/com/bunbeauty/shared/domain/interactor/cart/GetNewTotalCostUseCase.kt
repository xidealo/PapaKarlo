package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct

//TODO(add more tests)
class GetNewTotalCostUseCase(private val getDiscountUseCase: GetDiscountUseCase) {
    suspend operator fun invoke(productList: List<CartProduct>): Int {
        val newTotalCost = productList.sumOf { orderProductEntity ->
            getMenuProductPrice(orderProductEntity = orderProductEntity) +
                    getAdditionsPrice(orderProductEntity = orderProductEntity)
        }
        val discount =
            (newTotalCost * (getDiscountUseCase()?.firstOrderDiscount ?: 0) / 100.0).toInt()
        return newTotalCost - discount
    }

    private fun getMenuProductPrice(orderProductEntity: CartProduct) =
        orderProductEntity.count * orderProductEntity.product.newPrice

    private fun getAdditionsPrice(orderProductEntity: CartProduct) =
        orderProductEntity.cartProductAdditionList.sumOf { cartProductAddition ->
            cartProductAddition.price ?: 0
        }
}