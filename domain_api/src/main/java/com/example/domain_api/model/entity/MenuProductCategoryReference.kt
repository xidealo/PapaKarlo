package com.example.domain_api.model.entity

import androidx.room.Entity

@Entity(primaryKeys = ["menuProductUuid", "categoryUuid"])
data class MenuProductCategoryReference(
    val menuProductUuid: String,
    val categoryUuid: String,
)
