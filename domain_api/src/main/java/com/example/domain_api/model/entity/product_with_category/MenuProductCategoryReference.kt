package com.example.domain_api.model.entity.product_with_category

import androidx.room.Entity

@Entity(primaryKeys = ["menuProductUuid", "categoryUuid"])
data class MenuProductCategoryReference(
    val menuProductUuid: String,
    val categoryUuid: String,
)
