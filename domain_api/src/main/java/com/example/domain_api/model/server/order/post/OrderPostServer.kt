package com.example.domain_api.model.server.order.post

import kotlinx.serialization.SerialName

data class OrderPostServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("address")
    val address: String,

    @SerialName("comment")
    val comment: String?,

    @SerialName("deferredTime")
    val deferredTime: Long?,

    @SerialName("orderProducts")
    val orderProducts: List<OrderProductPostServer>,

    @SerialName("profileUuid")
    val userUuid: String,

    @SerialName("addressUuid")
    val addressUuid: String?,
)
