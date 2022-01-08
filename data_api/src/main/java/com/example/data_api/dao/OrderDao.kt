package com.example.data_api.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.user.order.OrderEntity
import com.example.domain_api.model.entity.user.order.OrderProductEntity
import com.example.domain_api.model.entity.user.order.OrderStatusUpdate
import com.example.domain_api.model.entity.user.order.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity WHERE userUuid = :userUuid ORDER BY time DESC")
    abstract fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderWithProducts>>

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
    abstract fun observeOrderByUuid(uuid: String): Flow<OrderWithProducts?>

    @Query(
        "SELECT * FROM OrderEntity " +
                "WHERE userUuid = :userUuid AND " +
                "time = (SELECT MAX(time) FROM OrderEntity WHERE userUuid = :userUuid)"
    )
    abstract fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderWithProducts?>

    // INSERT
    @Transaction
    open suspend fun insertOrder(orderWithProducts: OrderWithProducts) {
        insert(orderWithProducts.order)
        insertOrderProductList(orderWithProducts.orderProductList)
    }

    @Update(entity = OrderEntity::class)
    abstract suspend fun updateOrderStatus(orderStatusUpdate: OrderStatusUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract suspend fun insertOrderProductList(userAddressList: List<OrderProductEntity>)

}