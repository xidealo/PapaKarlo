package com.bunbeauty.data.mapper.order_product

import com.bunbeauty.data.network.model.order.get.OrderProductServer
import com.bunbeauty.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderMenuProduct
import com.bunbeauty.shared.domain.model.product.OrderProduct
import database.OrderWithProductEntity

class OrderProductMapper : IOrderProductMapper {

    override fun toOrderProduct(orderWithProductEntity: OrderWithProductEntity): OrderProduct {
        return OrderProduct(
            uuid = orderWithProductEntity.orderProductUuid,
            count = orderWithProductEntity.orderProductCount,
            product = OrderMenuProduct(
                name = orderWithProductEntity.orderProductName,
                newPrice = orderWithProductEntity.orderProductNewPrice,
                oldPrice = orderWithProductEntity.orderProductOldPrice,
                utils = orderWithProductEntity.orderProductUtils,
                nutrition = orderWithProductEntity.orderProductNutrition,
                description = orderWithProductEntity.orderProductDescription,
                comboDescription = orderWithProductEntity.orderProductComboDescription,
                photoLink = orderWithProductEntity.orderProductPhotoLink,
            ),
        )
    }

    override fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct {
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