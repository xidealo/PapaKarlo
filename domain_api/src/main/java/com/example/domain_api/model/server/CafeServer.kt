package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CafeServer(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("address")
    val address: String,

    @SerialName("fromTime")
    val fromTime: String,

    @SerialName("toTime")
    val toTime: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("visible")
    val visible: Boolean,

    @SerialName("city")
    val city: String,
)