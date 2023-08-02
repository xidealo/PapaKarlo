package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

data class OrderDetailsState(
    val orderProductItemList: List<OrderProductItem> = emptyList(),
    val orderInfo: OrderInfo? = null,
    val totalCost: String? = null,
    val deliveryCost: String? = null,
    val finalCost: String? = null,
    val isLoading: Boolean = true
) {

    data class OrderInfo(
        val code: String,
        val status: OrderStatus,
        val dateTime: DateTime,
        val deferredTime: TimeUI?,
        val address: OrderAddress,
        val comment: String?,
        val isDelivery: Boolean,
        val paymentMethod: PaymentMethodName?,
    )

    data class OrderProductItem(
        val uuid: String,
        val name: String,
        val newPrice: String,
        val oldPrice: String?,
        val newCost: String,
        val oldCost: String?,
        val photoLink: String,
        val count: String
    )
}
