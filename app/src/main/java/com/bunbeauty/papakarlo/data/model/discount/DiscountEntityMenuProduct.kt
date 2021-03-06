package com.bunbeauty.papakarlo.data.model.discount

import androidx.room.Entity

@Entity(primaryKeys = ["discountEntityUuid", "uuid"])
data class DiscountEntityMenuProduct(
    var discountEntityUuid: Long = 0,
    var uuid: String = "",
)