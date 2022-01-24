package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CafeServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("fromTime")
    val fromTime: Int,

    @SerialName("toTime")
    val toTime: Int,

    @SerialName("offset")
    val offset: Int,

    @SerialName("phone")
    val phone: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("address")
    val address: String,

    @SerialName("cityUuid")
    val cityUuid: String,

    @SerialName("isVisible")
    val isVisible: Boolean,
)