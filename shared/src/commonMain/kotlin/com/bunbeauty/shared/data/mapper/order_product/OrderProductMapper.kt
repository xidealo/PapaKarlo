package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.domain.model.addition.OrderAddition
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.model.product.OrderMenuProduct
import com.bunbeauty.shared.domain.model.product.OrderProduct

class OrderProductMapper : IOrderProductMapper {

    override fun toOrderProduct(orderWithProductEntity: List<OrderWithProductEntity>): List<OrderProduct> {
        return orderWithProductEntity.groupBy { orderWithProductEntity ->
            orderWithProductEntity.orderProductUuid
        }.map { (_, groupedOrderWithProductEntityList) ->
            val firstOrderWithProductEntity =
                groupedOrderWithProductEntityList.first()
            OrderProduct(
                uuid = firstOrderWithProductEntity.orderProductUuid,
                count = firstOrderWithProductEntity.orderProductCount,
                product = OrderMenuProduct(
                    name = firstOrderWithProductEntity.orderProductName,
                    newPrice = firstOrderWithProductEntity.orderProductNewPrice,
                    oldPrice = firstOrderWithProductEntity.orderProductOldPrice,
                    utils = firstOrderWithProductEntity.orderProductUtils,
                    nutrition = firstOrderWithProductEntity.orderProductNutrition,
                    description = firstOrderWithProductEntity.orderProductDescription,
                    comboDescription = firstOrderWithProductEntity.orderProductComboDescription,
                    photoLink = firstOrderWithProductEntity.orderProductPhotoLink,
                    newCommonPrice = firstOrderWithProductEntity.orderProductNewCommonPrice,
                    oldCommonPrice = firstOrderWithProductEntity.orderProductOldCommonPrice,
                    newTotalCost = firstOrderWithProductEntity.orderProductNewTotalCost,
                    oldTotalCost = firstOrderWithProductEntity.orderProductOldTotalCost,
                ),
                orderAdditionList = groupedOrderWithProductEntityList.mapNotNull { orderWithProductEntity ->
                    orderWithProductEntity.orderAdditionEntityUuid?.let { orderAdditionEntityUuid ->
                        OrderAddition(
                            uuid = orderAdditionEntityUuid,
                            name = orderWithProductEntity.orderAdditionEntityName ?: ""
                        )
                    }
                }
            )
        }
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
                newCommonPrice = orderProduct.newCommonPrice,
                oldCommonPrice = orderProduct.oldCommonPrice,
                newTotalCost = orderProduct.newTotalCost,
                oldTotalCost = orderProduct.oldTotalCost,
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