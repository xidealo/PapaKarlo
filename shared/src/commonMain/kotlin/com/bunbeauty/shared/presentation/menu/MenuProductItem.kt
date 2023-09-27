package com.bunbeauty.shared.presentation.menu

data class MenuProductItem(
    val uuid: String,
    val photoLink: String,
    val name: String,
    val oldPrice: Int?,
    val newPrice: Int,
)
