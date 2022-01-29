package com.bunbeauty.data.database.entity.product_with_category

import androidx.room.Entity

@Entity(primaryKeys = ["menuProductUuid", "categoryUuid"])
data class MenuProductCategoryReference(
    val menuProductUuid: String,
    val categoryUuid: String,
)
