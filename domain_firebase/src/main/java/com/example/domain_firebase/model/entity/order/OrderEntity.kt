package com.example.domain_firebase.model.entity.order

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.user.UserEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = CafeEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["cafeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["userUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OrderEntity(
    @PrimaryKey
    val uuid: String,
    val isDelivery: Boolean,
    val phone: String,
    val comment: String?,
    val deferredTime: String?,
    val time: Long,
    val code: String,
    val orderStatus: OrderStatus,
    val cafeUuid: String,
    val userUuid: String,

    val userAddressStreet: String?,
    val userAddressHouse: String?,
    val userAddressFlat: String?,
    val userAddressEntrance: String?,
    val userAddressFloor: String?,
    val userAddressComment: String?,
)