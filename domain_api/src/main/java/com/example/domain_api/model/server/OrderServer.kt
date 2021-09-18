package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OrderServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("status")
    val status: String,

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("time")
    val time: Long,

    @SerialName("code")
    val code: String,

    @SerialName("address")
    val address: String,

    @SerialName("userUuid")
    val userUuid: String
)
