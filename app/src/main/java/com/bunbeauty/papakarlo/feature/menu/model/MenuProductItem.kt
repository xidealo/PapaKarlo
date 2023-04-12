package com.bunbeauty.papakarlo.feature.menu.model

data class MenuProductItem(
    val uuid: String,
    val photoLink: String,
    val name: String,
    val oldPrice: String?,
    val newPrice: String,
)
