package com.example.data_api.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.OrderMenuProduct
import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_api.mapper.IOrderProductMapper
import com.example.domain_api.model.entity.user.order.OrderProductEntity
import com.example.domain_api.model.server.order.get.OrderProductServer
import com.example.domain_api.model.server.order.post.OrderProductPostServer
import javax.inject.Inject

class OrderProductMapper @Inject constructor() : IOrderProductMapper {

    override fun toEntityModel(orderProduct: OrderProductServer): OrderProductEntity {
        return OrderProductEntity(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            name = orderProduct.name,
            cost = orderProduct.cost,
            discountCost = orderProduct.discountCost,
            weight = orderProduct.weight,
            description = orderProduct.description,
            comboDescription = orderProduct.comboDescription,
            photoLink = orderProduct.photoLink,
            barcode = orderProduct.barcode,
            orderUuid = orderProduct.orderUuid,
        )
    }

    override fun toModel(orderProduct: OrderProductEntity): OrderProduct {
        return OrderProduct(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            menuProduct = OrderMenuProduct(
                name = orderProduct.name,
                cost = orderProduct.cost,
                discountCost = orderProduct.discountCost,
                weight = orderProduct.weight,
                description = orderProduct.description,
                comboDescription = orderProduct.comboDescription,
                photoLink = orderProduct.photoLink,
            ),
        )
    }

    override fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer {
        return OrderProductPostServer(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            menuProductUuid = ""
        )
    }

    override fun toPostServerModel(cartProduct: CartProduct): OrderProductPostServer {
        return OrderProductPostServer(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.menuProduct.uuid
        )
    }
}