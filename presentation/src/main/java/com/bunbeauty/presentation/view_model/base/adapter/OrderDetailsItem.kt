package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.enums.ActiveLines

data class OrderDetailsItem(
    val code: String,
    val orderStatus: String,
    val orderStatusBackground: Int,
    val orderStatusActiveLine: ActiveLines,
    val time: String,
    val pickupMethod: String,
    val deferredTime: String,
    val address: String,
    val comment: String,
    val deliveryCost: String,
    val oldTotalCost: String,
    val newTotalCost: String,
    val cartProducts: List<CartProductItem>,
    val isDelivery: Boolean
)
