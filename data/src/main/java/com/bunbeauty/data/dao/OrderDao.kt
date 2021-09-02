package com.bunbeauty.data.dao

import androidx.room.*
import com.bunbeauty.domain.model.entity.order.OrderEntity
import com.bunbeauty.domain.model.entity.order.OrderStatusEntity
import com.bunbeauty.domain.model.entity.order.OrderWithProducts
import com.bunbeauty.domain.model.entity.product.OrderProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    // INSERT

    @Transaction
    suspend fun insert(orderWithProducts: OrderWithProducts) {
        insert(orderWithProducts.order)
        insertOrderProductsList(orderWithProducts.orderProductList)
    }

    @Insert
    suspend fun insertOrderProductsList(orderProductList: List<OrderProductEntity>)

    // OBSERVE

    @Query("SELECT * FROM OrderEntity WHERE userUuid = :userUuid")
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderWithProducts>>

    @Query("SELECT * FROM OrderEntity WHERE userUuid = :uuid")
    fun observeOrderByUuid(uuid: String): Flow<OrderWithProducts?>

    @Query("SELECT * FROM OrderEntity WHERE time = (SELECT MAX(time) FROM OrderEntity)")
    fun observeLastOrder(): Flow<OrderWithProducts?>

    // GET

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
    suspend fun getOrderByUuid(uuid: String): OrderWithProducts?

    // UPDATE

    @Update(entity = OrderEntity::class)
    suspend fun updateOrderStatus(orderStatusEntity: OrderStatusEntity)
}