package com.example.data_api.mapper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.model.product.CartProduct
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
                status = OrderStatus.valueOf(order.orderStatus),
                isDelivery = order.isDelivery,
                time = order.time,
                code = order.code,
                address = order.address,
                comment = order.comment,
                deferredTime = order.deferredTime,
                userUuid = order.userUuid,
            ),
            orderProductList = order.orderProducts.map(orderProductMapper::toEntityModel)
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
            orderStatus = order.order.status,
            orderProductList = order.orderProductList.map(orderProductMapper::toModel),
            userUuid = order.order.userUuid,
            addressUuid = null,
        )
    }

    override fun toPostServerModel(
        orderDetails: OrderDetails,
        cartProductList: List<CartProduct>
    ): OrderPostServer {
        return OrderPostServer(
            uuid = "",
            isDelivery = orderDetails.isDelivery,
            address = orderDetails.address,
            comment = orderDetails.comment,
            deferredTime = orderDetails.deferredTime,
            orderProducts = cartProductList.map(orderProductMapper::toPostServerModel),
            profileUuid = orderDetails.profileUuid,
            addressUuid = orderDetails.userAddressUuid,
            cafeUuid = orderDetails.cafeUuid,
        )
    }
}