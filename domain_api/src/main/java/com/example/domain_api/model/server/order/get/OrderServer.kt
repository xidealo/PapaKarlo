package com.example.domain_api.model.server.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("time")
    val time: Long,

    @SerialName("orderStatus")
    val orderStatus: String,

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("code")
    val code: String,

    @SerialName("comment")
    val comment: String?,

    @SerialName("deferredTime")
    val deferredTime: Long?,

    @SerialName("address")
    val address: String,

    @SerialName("profileUuid")
    val userUuid: String,

    @SerialName("orderProducts")
    val orderProducts: List<OrderProductServer>,
)
