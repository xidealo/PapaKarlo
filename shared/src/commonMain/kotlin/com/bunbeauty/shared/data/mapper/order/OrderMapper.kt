package com.bunbeauty.shared.data.mapper.order

import com.bunbeauty.shared.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.order.*
import com.bunbeauty.shared.domain.util.IDateTimeUtil

class OrderMapper(
    private val orderProductMapper: IOrderProductMapper,
    private val dateTimeUtil: IDateTimeUtil,
) : IOrderMapper {

    override fun toOrderWithProductEntityList(orderServer: OrderServer): List<OrderWithProductEntity> {
        return orderServer.oderProductList.map { orderProductServer ->
            OrderWithProductEntity(
                uuid = orderServer.uuid,
                status = orderServer.status,
                isDelivery = orderServer.isDelivery,
                time = orderServer.time,
                timeZone = orderServer.timeZone,
                code = orderServer.code,
                address = orderServer.addressDescription,
                comment = orderServer.comment,
                deliveryCost = orderServer.deliveryCost,
                deferredTime = orderServer.deferredTime,
                userUuid = orderServer.clientUserUuid,
                orderProductUuid = orderServer.uuid,
                orderProductCount = orderProductServer.count,
                orderProductName = orderProductServer.name,
                orderProductNewPrice = orderProductServer.newPrice,
                orderProductOldPrice = orderProductServer.oldPrice,
                orderProductUtils = orderProductServer.utils,
                orderProductNutrition = orderProductServer.nutrition,
                orderProductDescription = orderProductServer.description,
                orderProductComboDescription = orderProductServer.comboDescription,
                orderProductPhotoLink = orderProductServer.photoLink,
                orderProductBarcode = orderProductServer.barcode,
                orderUuid = orderProductServer.uuid
            )
        }
    }

    override fun toLightOrder(orderEntity: OrderEntity): LightOrder {
        return LightOrder(
            uuid = orderEntity.uuid,
            status = OrderStatus.valueOf(orderEntity.status),
            code = orderEntity.code,
            dateTime = dateTimeUtil.toDateTime(orderEntity.time, orderEntity.timeZone)
        )
    }

    override fun toLightOrder(orderServer: OrderServer): LightOrder {
        return LightOrder(
            uuid = orderServer.uuid,
            status = OrderStatus.valueOf(orderServer.status),
            code = orderServer.code,
            dateTime = dateTimeUtil.toDateTime(orderServer.time, orderServer.timeZone)
        )
    }

    override fun toOrderCode(orderServer: OrderServer): OrderCode {
        return OrderCode(
            code = orderServer.code
        )
    }

    override fun toOrder(orderWithProductEntityList: List<OrderWithProductEntity>): Order? {
        return orderWithProductEntityList.firstOrNull()?.let { firstOrderWithProductEntity ->
            Order(
                uuid = firstOrderWithProductEntity.uuid,
                code = firstOrderWithProductEntity.code,
                status = OrderStatus.valueOf(firstOrderWithProductEntity.status),
                dateTime = dateTimeUtil.toDateTime(
                    millis = firstOrderWithProductEntity.time,
                    timeZone = firstOrderWithProductEntity.timeZone
                ),
                isDelivery = firstOrderWithProductEntity.isDelivery,
                deferredTime = firstOrderWithProductEntity.deferredTime?.let { millis ->
                    dateTimeUtil.toTime(millis, firstOrderWithProductEntity.timeZone)
                },
                address = firstOrderWithProductEntity.address,
                comment = firstOrderWithProductEntity.comment,
                deliveryCost = firstOrderWithProductEntity.deliveryCost,
                orderProductList = orderWithProductEntityList.map(orderProductMapper::toOrderProduct),
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
            orderProductList = orderServer.oderProductList.map(orderProductMapper::toOrderProduct),
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