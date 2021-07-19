package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.OrderFirebase
import com.bunbeauty.domain.model.local.order.Order
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val orderEntityMapper: OrderEntityMapper,
    private val cartProductMapper: CartProductMapper
) : Mapper<OrderFirebase, Order> {

    override fun from(model: OrderFirebase): Order {
        return Order(
            orderEntity = orderEntityMapper.from(model.orderEntity),
            cartProducts = model.cartProducts.map { cartProductMapper.from(it) },
            timestamp = model.timestamp,
            uuid = "empty uuid",
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(model: Order): OrderFirebase {
        return OrderFirebase(
            orderEntityMapper.to(model.orderEntity),
            model.cartProducts.map { cartProductMapper.to(it) },
            model.timestamp,
        )
    }
}