package com.bunbeauty.shared.data.mapper.order

import com.bunbeauty.shared.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderAddressPostServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.domain.util.IDateTimeUtil

class OrderMapper(
    private val orderProductMapper: IOrderProductMapper,
    private val dateTimeUtil: IDateTimeUtil,
) : IOrderMapper {

    override fun toOrderWithProductEntity(
        orderServer: OrderServer,
        orderProductServer: OrderProductServer,
    ): OrderWithProductEntity {
        return OrderWithProductEntity(
            uuid = orderServer.uuid,
            status = orderServer.status,
            isDelivery = orderServer.isDelivery,
            time = orderServer.time,
            timeZone = orderServer.timeZone,
            code = orderServer.code,
            address = orderServer.address.description ?: "",
            addressStreet = orderServer.address.street,
            addressHouse = orderServer.address.house,
            addressFlat = orderServer.address.flat,
            addressEntrance = orderServer.address.entrance,
            addressFloor = orderServer.address.floor,
            addressComment = orderServer.address.comment,
            comment = orderServer.comment,
            deliveryCost = orderServer.deliveryCost,
            deferredTime = orderServer.deferredTime,
            userUuid = orderServer.clientUserUuid,
            orderProductUuid = orderProductServer.uuid,
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
            orderUuid = orderServer.uuid,
            paymentMethod = orderServer.paymentMethod,
            oldTotalCost = orderServer.oldTotalCost,
            newTotalCost = orderServer.newTotalCost,
            percentDiscount = orderServer.percentDiscount,
        )
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
                address = OrderAddress(
                    description = firstOrderWithProductEntity.address,
                    street = firstOrderWithProductEntity.addressStreet,
                    house = firstOrderWithProductEntity.addressHouse,
                    flat = firstOrderWithProductEntity.addressFlat,
                    entrance = firstOrderWithProductEntity.addressEntrance,
                    floor = firstOrderWithProductEntity.addressFloor,
                    comment = firstOrderWithProductEntity.addressComment,
                ),
                comment = firstOrderWithProductEntity.comment,
                deliveryCost = firstOrderWithProductEntity.deliveryCost,
                orderProductList = orderWithProductEntityList.map(orderProductMapper::toOrderProduct),
                paymentMethod = PaymentMethodName.values()
                    .firstOrNull { it.name == firstOrderWithProductEntity.paymentMethod },
                oldTotalCost = firstOrderWithProductEntity.oldTotalCost,
                newTotalCost = firstOrderWithProductEntity.newTotalCost,
                percentDiscount = firstOrderWithProductEntity.percentDiscount,
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
            address = OrderAddress(
                description = orderServer.address.description,
                street = orderServer.address.street,
                house = orderServer.address.house,
                flat = orderServer.address.flat,
                entrance = orderServer.address.entrance,
                floor = orderServer.address.floor,
                comment = orderServer.address.comment,
            ),
            comment = orderServer.comment,
            deliveryCost = orderServer.deliveryCost,
            orderProductList = orderServer.oderProductList.map(orderProductMapper::toOrderProduct),
            paymentMethod = PaymentMethodName.values()
                .firstOrNull { it.name == orderServer.paymentMethod },
            oldTotalCost = orderServer.oldTotalCost,
            newTotalCost = orderServer.newTotalCost,
            percentDiscount = orderServer.percentDiscount,
        )
    }

    override fun toOrderPostServer(createdOrder: CreatedOrder): OrderPostServer {
        return OrderPostServer(
            isDelivery = createdOrder.isDelivery,
            address = OrderAddressPostServer(
                uuid = createdOrder.address.uuid,
                description = createdOrder.address.description,
                street = createdOrder.address.street,
                house = createdOrder.address.house,
                flat = createdOrder.address.flat,
                entrance = createdOrder.address.entrance,
                floor = createdOrder.address.floor,
                comment = createdOrder.address.comment,
            ),
            comment = createdOrder.comment,
            deferredTime = createdOrder.deferredTime,
            orderProducts = createdOrder.orderProducts.map(orderProductMapper::toPostServerModel),
            paymentMethod = createdOrder.paymentMethod
        )
    }
}