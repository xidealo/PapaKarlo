package com.bunbeauty.shared.presentation.consumercart

data class CartProductItem(
    val uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
    val additions: String?,
    val additionUuidList: List<String>,
)
