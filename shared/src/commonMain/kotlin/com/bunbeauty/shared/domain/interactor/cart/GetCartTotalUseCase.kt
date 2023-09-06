package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo

class GetCartTotalUseCase(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
    private val getDiscountUseCase: GetDiscountUseCase,
) {

    suspend operator fun invoke(isDelivery: Boolean): CartTotal {
        val cartProductList = cartProductRepo.getCartProductList()

        if (cartProductList.isEmpty()) {
            //Выкинуть ошибку и в корзине обработать как пустая корзина
            error("Cart is empty")
        }
        val newTotalCost = getNewTotalCost(cartProductList)
        val oldTotalCost = getOldTotalCost(cartProductList)

        val deliveryCost = getDeliveryCost(isDelivery, newTotalCost)

        return CartTotal(
            totalCost = getTotalCost(cartProductList),
            deliveryCost = deliveryCost,
            oldFinalCost = oldTotalCost?.let {
                oldTotalCost + deliveryCost
            },
            newFinalCost = newTotalCost + deliveryCost,
        )
    }

    private suspend fun getDeliveryCost(isDelivery: Boolean, newTotalCost: Int): Int {
        val delivery = deliveryRepo.getDelivery() ?: error("Delivery info is not found")
        return if (isDelivery && newTotalCost < delivery.forFree) {
            delivery.cost
        } else {
            0
        }
    }

    fun getTotalCost(productList: List<CartProduct>): Int {
        return productList.sumOf { orderProductEntity ->
            orderProductEntity.count * orderProductEntity.product.newPrice
        }
    }

    suspend fun getNewTotalCost(productList: List<CartProduct>): Int {
        val newTotalCost = productList.sumOf { orderProductEntity ->
            orderProductEntity.count * orderProductEntity.product.newPrice
        }

        val discount =
            (newTotalCost * (getDiscountUseCase()?.firstOrderDiscount ?: 0) / 100.0).toInt()

        return newTotalCost - discount
    }

    suspend fun getOldTotalCost(productList: List<CartProduct>): Int? {
        val oldTotalCost = productList.sumOf { orderProductEntity ->
            orderProductEntity.count * (orderProductEntity.product.oldPrice
                ?: orderProductEntity.product.newPrice)
        }

        return if (oldTotalCost == getNewTotalCost(productList)) {
            null
        } else {
            oldTotalCost
        }
    }
}