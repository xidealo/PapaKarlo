package com.bunbeauty.papakarlo.feature.profile.order.order_details

class OrderProductItemModel(
    val uuid: String,
    val name: String,
    val newPrice: String,
    val oldPrice: String?,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: String
)