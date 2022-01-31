package com.bunbeauty.domain.model.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.product.OrderProduct

data class OrderDetails(
    val uuid: String,
    val code: String,
    val status: OrderStatus,
    val dateTime: String,
    val isDelivery: Boolean,
    val deferredTime: String?,
    val address: String,
    val comment: String?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val orderProductList: List<OrderProduct>
)
