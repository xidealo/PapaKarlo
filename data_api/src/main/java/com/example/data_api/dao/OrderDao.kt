package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.user.order.OrderEntity
import com.example.domain_api.model.entity.user.order.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<OrderEntity> {

    @Query("SELECT * FROM OrderEntity WHERE userUuid = :userUuid ORDER BY time DESC")
    fun observeOrderListByUserUuid(userUuid: String): Flow<List<OrderWithProducts>>

    @Query("SELECT * FROM OrderEntity WHERE uuid = :uuid")
    fun observeOrderByUuid(uuid: String): Flow<OrderWithProducts?>

    @Query(
        "SELECT * FROM OrderEntity " +
                "WHERE userUuid = :userUuid AND " +
                "time = (SELECT MAX(time) FROM OrderEntity WHERE userUuid = :userUuid)"
    )
    fun observeLastOrderByUserUuid(userUuid: String): Flow<OrderWithProducts?>
}