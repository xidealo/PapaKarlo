package com.example.domain_api.model.entity.user.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus

@Entity
data class OrderStatusUpdate(
    @PrimaryKey
    val uuid: String,
    val status: OrderStatus,
)
