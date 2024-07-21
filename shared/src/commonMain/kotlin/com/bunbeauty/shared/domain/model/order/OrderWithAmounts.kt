package com.bunbeauty.shared.domain.model.order

import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.product.OrderProductWithCosts

data class OrderWithAmounts(
    val uuid: String,
    val code: String,
    val status: OrderStatus,
    val dateTime: DateTime,
    val isDelivery: Boolean,
    val deferredTime: Time?,
    val address: String,
    val comment: String?,
    val deliveryCost: Int?,
    val orderProductList: List<OrderProductWithCosts>,
    val oldAmountToPay: Int?,
    val newAmountToPay: Int
)
