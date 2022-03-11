package com.bunbeauty.papakarlo.feature.product_details

data class MenuProductUI(
    val photoLink: String,
    val name: String,
    val size: String,
    val oldPrice: String?,
    val newPrice: String,
    val description: String
)
