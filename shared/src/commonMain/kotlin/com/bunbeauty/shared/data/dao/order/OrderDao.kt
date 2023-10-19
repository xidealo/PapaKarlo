package com.bunbeauty.shared.data.dao.order

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderProductEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
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
                        OrderEntity(
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
                            addressStreet = firstOrderWithProduct.addressStreet,
                            addressHouse = firstOrderWithProduct.addressHouse,
                            addressFlat = firstOrderWithProduct.addressFlat,
                            addressEntrance = firstOrderWithProduct.addressEntrance,
                            addressFloor = firstOrderWithProduct.addressFloor,
                            addressComment = firstOrderWithProduct.addressComment,
                            paymentMethod = firstOrderWithProduct.paymentMethod,
                            oldTotalCost = firstOrderWithProduct.oldTotalCost,
                            newTotalCost = firstOrderWithProduct.newTotalCost,
                            percentDiscount = firstOrderWithProduct.percentDiscount,
                        )
                    )
                }
                groupedOrderWithProductList.onEach { orderWithProduct ->
                    orderEntityQueries.insertOrderProduct(
                        OrderProductEntity(
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
                    )
                }
            }
        }
    }

    override suspend fun getLastOrderByUserUuid(userUuid: String): OrderEntity? {
        return orderEntityQueries
            .getLastOrderWithProductListByUserUuid(userUuid)
            .executeAsOneOrNull()
    }

    override suspend fun getOrderListByUserUuid(userUuid: String): List<OrderEntity> {
        return orderEntityQueries.getOrderListByUserUuid(userUuid).executeAsList()
    }

    override suspend fun getOrderWithProductListByUserUuid(userUuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductListByUserUuid(userUuid).executeAsList()
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

    override fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderEntity?> {
        return orderEntityQueries.getLastOrderWithProductListByUserUuid(userUuid).asFlow()
            .mapToOneOrNull()
    }

    override suspend fun getOrderWithProductListByUuid(uuid: String): List<OrderWithProductEntity> {
        return orderEntityQueries.getOrderWithProductByUuid(uuid).executeAsList()
    }

    override suspend fun updateOrderStatusByUuid(uuid: String, status: String) {
        orderEntityQueries.updateOrderStatusByUuid(
            uuid = uuid,
            status = status
        )
    }
}