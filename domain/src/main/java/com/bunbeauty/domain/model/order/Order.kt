package com.bunbeauty.domain.model.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.domain.model.product.OrderProduct

data class Order(
    val uuid: String,
    val code: String,
    val status: OrderStatus,
    val dateTime: DateTime,
    val isDelivery: Boolean,
    val deferredTime: Time?,
    val address: String,
    val comment: String?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val orderProductList: List<OrderProduct>
)
