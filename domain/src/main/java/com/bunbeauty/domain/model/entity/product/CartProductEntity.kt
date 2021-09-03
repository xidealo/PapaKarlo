package com.bunbeauty.domain.model.entity.product

import androidx.room.*
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import com.bunbeauty.domain.model.entity.order.OrderEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = MenuProductEntity::class,
        parentColumns = ["uuid"],
        childColumns = ["menuProductUuid"]
    )]
)
data class CartProductEntity(
    @PrimaryKey
    val uuid: String,
    val count: Int,
    val menuProductUuid: String
)