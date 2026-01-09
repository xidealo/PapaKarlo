package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.core.model.addition.OrderAddition
import com.bunbeauty.core.model.product.CreatedOrderProduct
import com.bunbeauty.core.model.product.OrderMenuProduct
import com.bunbeauty.core.model.product.OrderProduct
import com.bunbeauty.shared.data.mapper.orderaddition.mapOrderAdditionServerToOrderAddition
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.order.post.OrderProductPostServer
import com.bunbeauty.shared.db.OrderWithProductEntity

class OrderProductMapper : IOrderProductMapper {
    override fun toOrderProduct(orderWithProductEntityList: List<OrderWithProductEntity>): List<OrderProduct> =
        orderWithProductEntityList
            .groupBy { orderWithProductEntity ->
                orderWithProductEntity.orderProductUuid
            }.map { (_, groupedOrderWithProductEntityList) ->
                val firstOrderWithProductEntity =
                    groupedOrderWithProductEntityList.first()
                OrderProduct(
                    uuid = firstOrderWithProductEntity.orderProductUuid,
                    count = firstOrderWithProductEntity.orderProductCount,
                    product =
                        OrderMenuProduct(
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
                    orderAdditionList =
                        groupedOrderWithProductEntityList.mapNotNull { orderWithProductEntity ->
                            orderWithProductEntity.orderAdditionEntityUuid?.let { orderAdditionEntityUuid ->
                                OrderAddition(
                                    uuid = orderAdditionEntityUuid,
                                    name = orderWithProductEntity.orderAdditionEntityName ?: "",
                                    priority = orderWithProductEntity.orderAdditionEntityPriority
                                        ?: 0,
                                )
                            }
                        },
                )
            }

    override fun toOrderProduct(orderProduct: OrderProductServer): OrderProduct =
        OrderProduct(
            uuid = orderProduct.uuid,
            count = orderProduct.count,
            product =
                OrderMenuProduct(
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
            orderAdditionList = orderProduct.additions.map(mapOrderAdditionServerToOrderAddition),
        )

    override fun toPostServerModel(createdOrderProduct: CreatedOrderProduct): OrderProductPostServer =
        OrderProductPostServer(
            menuProductUuid = createdOrderProduct.menuProductUuid,
            count = createdOrderProduct.count,
            additionUuids = createdOrderProduct.additionUuids,
        )
}
