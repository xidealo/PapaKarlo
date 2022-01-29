package com.bunbeauty.data.mapper.order

import com.bunbeauty.common.Constants
import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts
import com.bunbeauty.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import org.joda.time.DateTime
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val orderProductMapper: IOrderProductMapper
) : IOrderMapper {

    override fun toEntityModel(order: OrderServer): OrderWithProducts {
        return OrderWithProducts(
            order = OrderEntity(
                uuid = order.uuid,
                status = OrderStatus.valueOf(order.status),
                isDelivery = order.isDelivery,
                time = order.time,
                code = order.code,
                address = order.addressDescription,
                comment = order.comment,
                deliveryCost = order.deliveryCost,
                deferredTime = order.deferredTime,
                userUuid = order.clientUserUuid,
            ),
            orderProductList = order.oderProductList.map(orderProductMapper::toEntityModel)
        )
    }

    override fun toLightOrder(order: Order): LightOrder {
        return LightOrder(
            uuid = order.uuid,
            status = order.status,
            code = order.code,
            dateTime = getOrderDateTime(order.time)
        )
    }

    override fun toLightOrder(order: OrderWithProducts): LightOrder {
        return LightOrder(
            uuid = order.order.uuid,
            status = order.order.status,
            code = order.order.code,
            dateTime = getOrderDateTime(order.order.time)
        )
    }

    fun getOrderDateTime(time: Long): String {
        return DateTime(time).toString(Constants.DD_MMMM_HH_MM_PATTERN)
    }

    override fun toOrderStatusUpdate(order: Order): OrderStatusUpdate {
        return OrderStatusUpdate(
            uuid = order.uuid,
            status = order.status,
        )
    }

    override fun toModel(order: OrderWithProducts): Order {
        return Order(
            uuid = order.order.uuid,
            isDelivery = order.order.isDelivery,
            address = order.order.address,
            comment = order.order.comment,
            deferredTime = order.order.deferredTime,
            time = order.order.time,
            code = order.order.code,
            status = order.order.status,
            deliveryCost = order.order.deliveryCost,
            orderProductList = order.orderProductList.map(orderProductMapper::toModel),
            userUuid = order.order.userUuid,
            addressUuid = null,
        )
    }

    override fun toModel(order: OrderServer): Order {
        return Order(
            uuid = order.uuid,
            isDelivery = order.isDelivery,
            address = order.addressDescription,
            comment = order.comment,
            deferredTime = order.deferredTime,
            time = order.time,
            code = order.code,
            status = OrderStatus.valueOf(order.status),
            deliveryCost = order.deliveryCost,
            orderProductList = order.oderProductList.map(orderProductMapper::toModel),
            userUuid = order.clientUserUuid,
            addressUuid = null,
        )
    }

    override fun toPostServerModel(createdOrder: CreatedOrder): OrderPostServer {
        return OrderPostServer(
            isDelivery = createdOrder.isDelivery,
            addressDescription = createdOrder.addressDescription,
            comment = createdOrder.comment,
            deferredTime = createdOrder.deferredTime,
            addressUuid = createdOrder.userAddressUuid,
            cafeUuid = createdOrder.cafeUuid,
            orderProducts = createdOrder.orderProducts.map(orderProductMapper::toPostServerModel),
        )
    }
}