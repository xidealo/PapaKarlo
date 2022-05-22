package com.bunbeauty.papakarlo.feature.product_details.model

data class MenuProductUI(
    val uuid: String,
    val photoLink: String,
    val name: String,
    val size: String,
    val oldPrice: String?,
    val newPrice: String,
    val description: String
)
