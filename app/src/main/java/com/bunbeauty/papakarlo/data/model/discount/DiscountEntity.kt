package com.bunbeauty.papakarlo.data.model.discount

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiscountEntity(
    @PrimaryKey
    var discountEntityUuid: String = "",
    var discountPercent: Float = 0f
)
