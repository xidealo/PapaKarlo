package com.bunbeauty.data_firebase.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.example.domain_firebase.model.entity.order.OrderEntity
import com.example.domain_firebase.model.entity.order.OrderStatusEntity
import com.example.domain_firebase.model.entity.order.OrderWithProducts
import com.example.domain_firebase.model.entity.product.OrderProductEntity
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

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
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