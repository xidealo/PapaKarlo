package com.bunbeauty.papakarlo.feature.order.model

class OrderProductItem(
    val uuid: String,
    val name: String,
    val newPrice: String,
    val oldPrice: String?,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: String
)