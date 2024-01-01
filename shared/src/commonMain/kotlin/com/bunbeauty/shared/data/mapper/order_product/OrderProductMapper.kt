package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.addition.OrderAddition
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderMenuProduct
import com.bunbeauty.shared.domain.model.product.OrderProduct

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
            orderAdditionList = emptyList()
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
            orderAdditionList = orderProduct.additions.map { orderAdditionsServer ->
                OrderAddition(
                    uuid = orderAdditionsServer.uuid,
                    name = orderAdditionsServer.name
                )
            }
        )
    }


    override fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer {
        return OrderProductPostServer(
            menuProductUuid = createdOrderProduct.menuProductUuid,
            count = createdOrderProduct.count,
            additionUuids = createdOrderProduct.additionUuids
        )
    }
}