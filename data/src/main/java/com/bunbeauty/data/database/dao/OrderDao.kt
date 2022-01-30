package com.bunbeauty.data.database.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderProductEntity
import com.bunbeauty.data.database.entity.user.order.OrderStatusUpdate
import com.bunbeauty.data.database.entity.user.order.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity WHERE userUuid = :userUuid ORDER BY time DESC")
    abstract fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderWithProducts>>

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
    abstract suspend fun getOrderByUuid(uuid: String): OrderWithProducts?

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