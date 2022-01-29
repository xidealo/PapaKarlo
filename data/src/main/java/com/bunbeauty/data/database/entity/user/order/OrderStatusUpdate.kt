package com.bunbeauty.data.database.entity.user.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus

@Entity
data class OrderStatusUpdate(
    @PrimaryKey
    val uuid: String,
    val status: OrderStatus,
)
