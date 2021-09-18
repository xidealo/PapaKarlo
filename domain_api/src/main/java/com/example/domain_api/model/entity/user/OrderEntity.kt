package com.example.domain_api.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus

@Entity
data class OrderEntity(
    @PrimaryKey
    var uuid: String,
    var status: OrderStatus,
    var isDelivery: Boolean,
    var time: Long,
    var code: String,
    var address: String,
    var userUuid: String
)
