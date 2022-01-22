package com.example.domain_api.model.server.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("code")
    val code: String,

    @SerialName("status")
    val status: String,

    @SerialName("time")
    val time: Long,

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("deferredTime")
    val deferredTime: Long?,

    @SerialName("addressDescription")
    val addressDescription: String,

    @SerialName("comment")
    val comment: String?,

    @SerialName("deliveryCost")
    val deliveryCost: Int?,

    @SerialName("clientUserUuid")
    val clientUserUuid: String,

    @SerialName("oderProductList")
    val oderProductList: List<OrderProductServer>,
)
