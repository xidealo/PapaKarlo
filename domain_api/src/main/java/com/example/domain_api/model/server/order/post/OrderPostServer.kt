package com.example.domain_api.model.server.order.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
    val profileUuid: String,

    @SerialName("addressUuid")
    val addressUuid: String?,

    @SerialName("cafeUuid")
    val cafeUuid: String?,
)
