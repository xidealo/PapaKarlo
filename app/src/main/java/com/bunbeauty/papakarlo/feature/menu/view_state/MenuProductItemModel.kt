package com.bunbeauty.papakarlo.feature.menu.view_state

data class MenuProductItemModel(
    val uuid: String,
    val photoLink: String,
    val name: String,
    val oldPrice: String?,
    val newPrice: String,
)
