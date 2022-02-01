package com.bunbeauty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityServer(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String
)