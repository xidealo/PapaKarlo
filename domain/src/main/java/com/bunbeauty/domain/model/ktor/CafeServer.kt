package com.bunbeauty.domain.model.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CafeServer(
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