package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.order_details.OrderDetails

data class OrderDetailsUi(
    val orderUuid: String,
    val orderProductItemList: List<OrderProductUiItem>,
    val oldTotalCost: String?,
    val deliveryCost: String?,
    val newTotalCost: String,
    val state: OrderDetails.DataState.ScreenState,
    val code: String,
    val orderInfo: OrderInfo?,
    val discount: String?
) : BaseViewState {
    data class OrderInfo(
        val status: OrderStatus,
        val statusName: String,
        val dateTime: String,
        val deferredTime: String?,
        val address: String,
        val comment: String?,
        val pickupMethod: String,
        val deferredTimeHint: String,
        val paymentMethod: String?
    )
}
