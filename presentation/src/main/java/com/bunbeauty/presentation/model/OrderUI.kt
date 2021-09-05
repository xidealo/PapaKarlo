package com.bunbeauty.presentation.model

import com.bunbeauty.presentation.item.OrderProductItem

data class OrderUI(
    val code: String,
    val stepCount: Int,
    val status: String,
    val orderStatusBackground: Int,
    val time: String,
    val pickupMethod: String,
    val deferredTime: String,
    val address: String,
    val comment: String,
    val deliveryCost: String,
    val oldTotalCost: String,
    val newTotalCost: String,
    val orderProductList: List<OrderProductItem>,
    val isDelivery: Boolean
)
