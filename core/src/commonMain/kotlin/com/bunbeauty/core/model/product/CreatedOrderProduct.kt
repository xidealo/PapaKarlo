package com.bunbeauty.core.model.product

data class CreatedOrderProduct(
    val menuProductUuid: String,
    val count: Int,
    val additionUuids: List<String>,
)
