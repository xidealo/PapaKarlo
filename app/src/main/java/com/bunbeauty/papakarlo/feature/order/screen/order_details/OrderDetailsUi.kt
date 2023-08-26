package com.bunbeauty.papakarlo.feature.order.screen.order_details

import androidx.annotation.StringRes
import com.bunbeauty.shared.domain.model.order.OrderStatus

data class OrderDetailsUi(
    val orderProductItemList: List<OrderProductUiItem>,
    val totalCost: String?,
    val deliveryCost: String?,
    val finalCost: String?,
    val isLoading: Boolean,
    val code: String,
    val orderInfo: OrderInfo?
) {
    data class OrderInfo(
        val status: OrderStatus,
        val statusName: String,
        val dateTime: String,
        val deferredTime: String?,
        val address: String,
        val comment: String?,
        val pickupMethod: String,
        @StringRes val deferredTimeHintId: Int,
        val paymentMethod: String?
    )
}
