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
import javax.inject.Inject

class OrderMapper @Inject constructor(
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
        return LightOrder(
            uuid = orderEntityWithProducts.orderEntity.uuid,
            status = orderEntityWithProducts.orderEntity.status,
            code = orderEntityWithProducts.orderEntity.code,
            dateTime = dateTimeUtil.toDateTime(orderEntityWithProducts.orderEntity.time)
        )
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
        return Order(
            uuid = orderEntityWithProducts.orderEntity.uuid,
            code = orderEntityWithProducts.orderEntity.code,
            status = orderEntityWithProducts.orderEntity.status,
            dateTime = dateTimeUtil.toDateTime(orderEntityWithProducts.orderEntity.time),
            isDelivery = orderEntityWithProducts.orderEntity.isDelivery,
            deferredTime = orderEntityWithProducts.orderEntity.deferredTime?.let { millis ->
                dateTimeUtil.toTime(millis)
            },
            address = orderEntityWithProducts.orderEntity.address,
            comment = orderEntityWithProducts.orderEntity.comment,
            deliveryCost = orderEntityWithProducts.orderEntity.deliveryCost,
            oldTotalCost = 0,
            newTotalCost = 0,
            orderProductList = orderEntityWithProducts.orderProductList.map(orderProductMapper::toModel),
        )
    }

    override fun toOrder(orderServer: OrderServer): Order {
        return Order(
            uuid = orderServer.uuid,
            code = orderServer.code,
            status = OrderStatus.valueOf(orderServer.status),
            dateTime = dateTimeUtil.toDateTime(orderServer.time),
            isDelivery = orderServer.isDelivery,
            deferredTime = orderServer.deferredTime?.let { millis ->
                dateTimeUtil.toTime(millis)
            },
            address = orderServer.addressDescription,
            comment = orderServer.comment,
            deliveryCost = orderServer.deliveryCost,
            oldTotalCost = 0,
            newTotalCost = 0,
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