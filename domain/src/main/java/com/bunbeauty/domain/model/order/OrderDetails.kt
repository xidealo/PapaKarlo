package com.bunbeauty.domain.model.order

data class OrderDetails(
    val isDelivery: Boolean,
    val profileUuid: String,
    val userAddressUuid: String?,
    val cafeUuid: String?,
    val address: String,
    val comment: String?,
    val deferredTime: Long?
)
