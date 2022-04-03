package com.bunbeauty.data.dao.order

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.OrderEntity
import database.OrderWithProductEntity
import kotlinx.coroutines.flow.Flow

class OrderDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IOrderDao {

    private val orderEntityQueries = foodDeliveryDatabase.orderEntityQueries

    override fun insertOrderWithProductList(orderWithProductList: List<OrderWithProductEntity>) {
        orderEntityQueries.transaction {
            orderWithProductList.groupBy { orderWithProduct ->
                orderWithProduct.uuid
            }.forEach { (_, groupedOrderWithProductList) ->
                groupedOrderWithProductList.first().let { firstOrderWithProduct ->
                    orderEntityQueries.isnsertOrder(
                        uuid = firstOrderWithProduct.uuid,
                        status = firstOrderWithProduct.status,
                        isDelivery = firstOrderWithProduct.isDelivery,
                        time = firstOrderWithProduct.time,
                        timeZone = firstOrderWithProduct.timeZone,
                        code = firstOrderWithProduct.code,
                        address = firstOrderWithProduct.address,
                        comment = firstOrderWithProduct.comment,
                        deliveryCost = firstOrderWithProduct.deliveryCost,
                        deferredTime = firstOrderWithProduct.deferredTime,
                        userUuid = firstOrderWithProduct.userUuid,
                    )
                }
                groupedOrderWithProductList.onEach { orderWithProduct ->
                    orderEntityQueries.insertOrderProduct(
                        uuid = orderWithProduct.orderProductUuid,
                        count = orderWithProduct.orderProductCount,
                        name = orderWithProduct.orderProductName,
                        newPrice = orderWithProduct.orderProductNewPrice,
                        oldPrice = orderWithProduct.orderProductOldPrice,
                        utils = orderWithProduct.orderProductUtils,
                        nutrition = orderWithProduct.orderProductNutrition,
                        description = orderWithProduct.orderProductDescription,
                        comboDescription = orderWithProduct.orderProductComboDescription,
                        photoLink = orderWithProduct.orderProductPhotoLink,
                        barcode = orderWithProduct.orderProductBarcode,
                        orderUuid = orderWithProduct.orderUuid,
                    )
                }
            }
        }
    }

    override suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity? {
        return orderEntityQueries.getLastOrderWithProductListByUserUuid(userUuid)
            .executeAsOneOrNull()
    }

    override fun observeOrderWithProductListByUserUuid(userUuid: String): Flow<List<OrderWithProductEntity>> {
        return orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).asFlow().mapToList()
    }

    override fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderEntity>> {
        return orderEntityQueries.getOrderListByUserUuid(userUuid).asFlow().mapToList()
    }

    override fun observeOrderWithProductListByUuid(uuid: String): Flow<List<OrderWithProductEntity>> {
        return orderEntityQueries.getOrderWithProductByUuid(uuid).asFlow().mapToList()
    }

    override fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductByUuid(uuid).executeAsList()
    }

    override fun updateOrderStatusByUuid(uuid: String, status: String) {
        orderEntityQueries.updateOrderStatusByUuid(
            uuid = uuid,
            status = status
        )
    }
}