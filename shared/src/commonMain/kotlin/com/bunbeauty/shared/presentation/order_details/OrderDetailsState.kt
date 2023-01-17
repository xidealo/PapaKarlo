package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

data class OrderDetailsState(
    val orderDetailsList: List<OrderDetails> = emptyList(),
    val totalCost: Int? = null,
    val deliveryCost: Int? = null,
    val finalCost: Int? = null,
    val isLoading: Boolean = true
)

sealed interface OrderDetails

data class OrderInfo(
    val code: String,
    val status: OrderStatus,
    val dateTime: DateTime,
    val deferredTime: TimeUI?,
    val address: OrderAddress,
    val comment: String?,
    val isDelivery: Boolean
): OrderDetails

class OrderProductItem(
    val uuid: String,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val newCost: Int,
    val oldCost: Int?,
    val photoLink: String,
    val count: Int
): OrderDetails