package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

class OrderProductUiItem(
    val uuid: String,
    val key: String,
    val name: String,
    val newPrice: String,
    val oldPrice: String?,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: String,
    val additions: String?,
    val isLast: Boolean,
)
