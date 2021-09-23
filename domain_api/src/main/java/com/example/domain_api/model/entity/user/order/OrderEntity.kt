package com.example.domain_api.model.entity.user.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus

@Entity
data class OrderEntity(
    @PrimaryKey
    val uuid: String,
    val status: OrderStatus,
    val isDelivery: Boolean,
    val time: Long,
    val code: String,
    val address: String,
    val comment: String?,
    val deferredTime: Long?,
    val userUuid: String,
)
