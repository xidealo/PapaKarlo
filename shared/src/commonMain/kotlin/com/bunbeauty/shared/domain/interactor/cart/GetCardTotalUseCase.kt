package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class GetCartTotalUseCase(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
) {

    suspend operator fun invoke(): CartTotal {
        val cartProductList = cartProductRepo.getCartProductList()
        if (cartProductList.isEmpty()) {
            error("Cart is empty")
        }
        val newTotalCost = cartProductList.sumOf { cartProduct ->
            cartProduct.count * cartProduct.product.newPrice
        }
        val delivery = deliveryRepo.getDelivery() ?: error("Delivery info is not found")
        val deliveryCost = if (newTotalCost > delivery.forFree) {
            0
        } else {
            delivery.cost
        }
        return CartTotal(
            totalCost = newTotalCost,
            deliveryCost = deliveryCost,
            finalCost = newTotalCost + deliveryCost
        )
    }
}