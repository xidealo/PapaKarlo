package com.bunbeauty.domain.model.entity.product

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bunbeauty.domain.model.entity.order.OrderEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = OrderEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["orderUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OrderProductEntity(
    @PrimaryKey
    val uuid: String,
    @Embedded(prefix = "menuProduct_")
    val menuProduct: MenuProductEntity,
    val count: Int,
    val orderUuid: String
)