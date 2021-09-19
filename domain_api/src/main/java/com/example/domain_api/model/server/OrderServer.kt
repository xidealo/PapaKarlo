package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OrderServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("orderStatus")
    val orderStatus: String,

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("time")
    val time: Long,

    @SerialName("code")
    val code: String,

    @SerialName("address")
    val address: String,

    @SerialName("profileUuid")
    val userUuid: String
)
