package com.bunbeauty.papakarlo.feature.profile.order.order_details

data class OrderUI(
    val code: String,
    val dateTime: String,
    val pickupMethod: String,
    val deferredTime: String?,
    val address: String,
    val comment: String?,
    val deliveryCost: String,
    val orderProductList: List<OrderProductItem>,
    val isDelivery: Boolean
)
