package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreetServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("cityUuid")
    val cityUuid: String,

    @SerialName("cafeUuid")
    val cafeUuid: String,

    @SerialName("isVisible")
    val isVisible: Boolean
)
