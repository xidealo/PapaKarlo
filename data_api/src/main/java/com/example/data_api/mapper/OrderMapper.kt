package com.example.data_api.mapper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.CreatedOrder
import com.bunbeauty.domain.model.order.Order
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.mapper.IOrderProductMapper
import com.example.domain_api.model.entity.user.order.OrderEntity
import com.example.domain_api.model.entity.user.order.OrderWithProducts
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer
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
                deferredTime = order.deferredTime,
                userUuid = order.clientUserUuid,
            ),
            orderProductList = order.oderProductList.map(orderProductMapper::toEntityModel)
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