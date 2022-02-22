package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderEntityWithProducts
import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.util.IDateTimeUtil

class OrderMapper  constructor(
    private val orderProductMapper: IOrderProductMapper,
    private val dateTimeUtil: IDateTimeUtil,
) : IOrderMapper {

    override fun toOrderEntityWithProducts(orderServer: OrderServer): OrderEntityWithProducts {
        return OrderEntityWithProducts(
            orderEntity = OrderEntity(
                uuid = orderServer.uuid,
                status = OrderStatus.valueOf(orderServer.status),
                isDelivery = orderServer.isDelivery,
                time = orderServer.time,
                timeZone = orderServer.timeZone,
                code = orderServer.code,
                address = orderServer.addressDescription,
                comment = orderServer.comment,
                deliveryCost = orderServer.deliveryCost,
                deferredTime = orderServer.deferredTime,
                userUuid = orderServer.clientUserUuid,
            ),
            orderProductList = orderServer.oderProductList.map(orderProductMapper::toEntityModel)
        )
    }

    override fun toLightOrder(orderEntityWithProducts: OrderEntityWithProducts): LightOrder {
        return orderEntityWithProducts.orderEntity.run {
            LightOrder(
                uuid = uuid,
                status = status,
                code = code,
                dateTime = dateTimeUtil.toDateTime(time, timeZone)
            )
        }
    }

    override fun toOrderStatusUpdate(orderServer: OrderServer): OrderStatusUpdate {
        return OrderStatusUpdate(
            uuid = orderServer.uuid,
            status = OrderStatus.valueOf(orderServer.status),
        )
    }

    override fun toOrderCode(orderServer: OrderServer): OrderCode {
        return OrderCode(
            code = orderServer.code
        )
    }

    override fun toOrder(orderEntityWithProducts: OrderEntityWithProducts): Order {
        return orderEntityWithProducts.run {
            Order(
                uuid = orderEntity.uuid,
                code = orderEntity.code,
                status = orderEntity.status,
                dateTime = dateTimeUtil.toDateTime(orderEntity.time, orderEntity.timeZone),
                isDelivery = orderEntity.isDelivery,
                deferredTime = orderEntity.deferredTime?.let { millis ->
                    dateTimeUtil.toTime(millis, orderEntity.timeZone)
                },
                address = orderEntity.address,
                comment = orderEntity.comment,
                deliveryCost = orderEntity.deliveryCost,
                orderProductList = orderProductList.map(orderProductMapper::toModel),
            )
        }
    }

    override fun toOrder(orderServer: OrderServer): Order {
        return Order(
            uuid = orderServer.uuid,
            code = orderServer.code,
            status = OrderStatus.valueOf(orderServer.status),
            dateTime = dateTimeUtil.toDateTime(orderServer.time, orderServer.timeZone),
            isDelivery = orderServer.isDelivery,
            deferredTime = orderServer.deferredTime?.let { millis ->
                dateTimeUtil.toTime(millis, orderServer.timeZone)
            },
            address = orderServer.addressDescription,
            comment = orderServer.comment,
            deliveryCost = orderServer.deliveryCost,
            orderProductList = orderServer.oderProductList.map(orderProductMapper::toModel),
        )
    }

    override fun toOrderPostServer(createdOrder: CreatedOrder): OrderPostServer {
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