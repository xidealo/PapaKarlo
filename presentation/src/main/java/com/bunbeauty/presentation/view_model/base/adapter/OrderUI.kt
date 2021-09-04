package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.enums.ActiveLines

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
    val cartProducts: List<CartProductItem>,
    val isDelivery: Boolean
)
