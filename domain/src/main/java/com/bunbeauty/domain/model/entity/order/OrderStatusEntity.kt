package com.bunbeauty.domain.model.entity.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus

@Entity
class OrderStatusEntity(
    @PrimaryKey
    val uuid: String,
    val orderStatus: OrderStatus,
)