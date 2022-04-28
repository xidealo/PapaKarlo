package com.bunbeauty.papakarlo.feature.profile.order.order_details

import androidx.annotation.StringRes
import com.bunbeauty.domain.model.order.OrderStatus

data class OrderUI(
    val code: String,
    val status: OrderStatus,
    val statusName: String,
    val dateTime: String,
    val pickupMethod: String,
    @StringRes val deferredTimeHintStringId: Int,
    val deferredTime: String?,
    val address: String,
    val comment: String?,
    val deliveryCost: String?,
    val orderProductList: List<OrderProductItemModel>,
    val isDelivery: Boolean,
    val oldAmountToPay: String?,
    val newAmountToPay: String,
)
