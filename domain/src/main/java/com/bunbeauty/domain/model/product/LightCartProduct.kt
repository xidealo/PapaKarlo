package com.bunbeauty.domain.model.product

data class LightCartProduct(
    val uuid: String,
    val name: String,
    val newCost: Int,
    val oldCost: Int?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
)
