package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.firebase.OrderFirebase
import com.bunbeauty.domain.model.order.Order
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val orderEntityMapper: OrderEntityMapper,
    private val cartProductMapper: CartProductMapper
) : Mapper<OrderFirebase, Order> {

    override fun from(e: Order): OrderFirebase {
        return OrderFirebase(
            orderEntityMapper.from(e.orderEntity),
            e.cartProducts.map { cartProductMapper.from(it) },
            e.timestamp,
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: OrderFirebase): Order {
        return Order(
            orderEntity = orderEntityMapper.to(t.orderEntity),
            cartProducts = t.cartProducts.map { cartProductMapper.to(it) },
            timestamp = t.timestamp,
            uuid = "empty uuid",
        )
    }
}