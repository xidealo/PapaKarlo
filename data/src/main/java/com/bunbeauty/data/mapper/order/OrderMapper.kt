package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts
import com.bunbeauty.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.data.network.model.order.get.OrderServer
import com.bunbeauty.data.network.model.order.post.OrderPostServer
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderCode
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.util.IDateTimeUtil
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val orderProductMapper: IOrderProductMapper,
    private val dateTimeUtil: IDateTimeUtil,
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

    override fun toLightOrder(order: OrderWithProducts): LightOrder {
        return LightOrder(
            uuid = order.order.uuid,
            status = order.order.status,
            code = order.order.code,
            dateTime = dateTimeUtil.toDDMMMMHHMM(order.order.time)
        )
    }

    override fun toOrderStatusUpdate(order: OrderServer): OrderStatusUpdate {
        return OrderStatusUpdate(
            uuid = order.uuid,
            status = OrderStatus.valueOf(order.status),
        )
    }

    override fun toOrderCode(order: OrderServer): OrderCode {
        return OrderCode(
            code = order.code
        )
    }

    override fun toOrderDetails(order: OrderWithProducts): OrderDetails {
        return OrderDetails(
            uuid = order.order.uuid,
            code = order.order.code,
            status = order.order.status,
            dateTime = dateTimeUtil.toDDMMMMHHMM(order.order.time),
            isDelivery = order.order.isDelivery,
            deferredTime = dateTimeUtil.toHHMM(order.order.deferredTime),
            address = order.order.address,
            comment = order.order.comment,
            deliveryCost = order.order.deliveryCost,
            oldTotalCost = 0,
            newTotalCost = 0,
            orderProductList = order.orderProductList.map(orderProductMapper::toModel),
        )
    }

    override fun toOrderDetails(order: OrderServer): OrderDetails {
        return OrderDetails(
            uuid = order.uuid,
            code = order.code,
            status = OrderStatus.valueOf(order.status),
            dateTime = dateTimeUtil.toDDMMMMHHMM(order.time),
            isDelivery = order.isDelivery,
            deferredTime = dateTimeUtil.toHHMM(order.deferredTime),
            address = order.addressDescription,
            comment = order.comment,
            deliveryCost = order.deliveryCost,
            oldTotalCost = 0,
            newTotalCost = 0,
            orderProductList = order.oderProductList.map(orderProductMapper::toModel),
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