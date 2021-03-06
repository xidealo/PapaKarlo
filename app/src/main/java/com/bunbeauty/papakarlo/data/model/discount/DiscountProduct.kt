package com.bunbeauty.papakarlo.data.model.discount

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiscountProduct(
    @PrimaryKey(autoGenerate = true)
    var discountProductId: Long = 0,
    var menuProductUuid: String = "",
    var discountEntityUuid: String = "",
    var cost: Long = 0
)