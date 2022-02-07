package com.bunbeauty.data.mapper.order_product

import com.bunbeauty.data.database.entity.user.order.OrderProductEntity
import com.bunbeauty.data.network.model.order.get.OrderProductServer
import com.bunbeauty.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.CreatedOrderProduct
import com.bunbeauty.domain.model.product.OrderMenuProduct
import com.bunbeauty.domain.model.product.OrderProduct

class OrderProductMapper  constructor() : IOrderProductMapper {

    override fun toEntityModel(orderProduct: OrderProductServer): OrderProductEntity {
        return OrderProductEntity(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            name = orderProduct.name,
            newPrice = orderProduct.newPrice,
            oldPrice = orderProduct.oldPrice,
            utils = orderProduct.utils,
            nutrition = orderProduct.nutrition,
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
            product = OrderMenuProduct(
                name = orderProduct.name,
                newPrice = orderProduct.newPrice,
                oldPrice = orderProduct.oldPrice,
                utils = orderProduct.utils,
                nutrition = orderProduct.nutrition,
                description = orderProduct.description,
                comboDescription = orderProduct.comboDescription,
                photoLink = orderProduct.photoLink,
            ),
        )
    }

    override fun toModel(orderProduct: OrderProductServer): OrderProduct {
        return OrderProduct(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            product = OrderMenuProduct(
                name = orderProduct.name,
                newPrice = orderProduct.newPrice,
                oldPrice = orderProduct.oldPrice,
                utils = orderProduct.utils,
                nutrition = orderProduct.nutrition,
                description = orderProduct.description,
                comboDescription = orderProduct.comboDescription,
                photoLink = orderProduct.photoLink,
            ),
        )
    }

    override fun toPostServerModel(orderProduct: OrderProduct): OrderProductPostServer {
        return OrderProductPostServer(
            count = orderProduct.count,
            menuProductUuid = ""
        )
    }

    override fun toPostServerModel(cartProduct: CartProduct): OrderProductPostServer {
        return OrderProductPostServer(
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid
        )
    }

    override fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer {
        return OrderProductPostServer(
            menuProductUuid = createdOrderProduct.menuProductUuid,
            count = createdOrderProduct.count,
        )
    }
}