package com.bunbeauty.shared.domain.model.address

data class UserAddress(
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val minOrderCost: Int?,
    val normalDeliveryCost: Int,
    val forLowDeliveryCost: Int?,
    val lowDeliveryCost: Int?,
    val userUuid: String,
    val cafeUuid: String
)
