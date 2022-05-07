package com.bunbeauty.shared.domain.model.order

import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct

data class CreatedOrder(
    val isDelivery: Boolean,
    val userAddressUuid: String?,
    val cafeUuid: String?,
    val addressDescription: String,
    val comment: String?,
    val deferredTime: Long?,
    val orderProducts: List<CreatedOrderProduct>
)
