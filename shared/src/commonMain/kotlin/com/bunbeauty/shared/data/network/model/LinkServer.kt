package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LinkServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String,
)
