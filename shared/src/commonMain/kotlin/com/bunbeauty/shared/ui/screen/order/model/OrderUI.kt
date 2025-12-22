package com.bunbeauty.shared.ui.screen.order.model

import com.bunbeauty.shared.domain.model.order.OrderStatus
import org.jetbrains.compose.resources.StringResource

data class OrderUI(
    val code: String,
    val status: OrderStatus,
    val statusName: String,
    val dateTime: String,
    val pickupMethod: String,
    val deferredTimeHintStringId: StringResource,
    val deferredTime: String?,
    val address: String,
    val comment: String?,
    val deliveryCost: String?,
    val orderProductList: List<OrderProductItem>,
    val isDelivery: Boolean,
    val oldAmountToPay: String?,
    val newAmountToPay: String,
)
