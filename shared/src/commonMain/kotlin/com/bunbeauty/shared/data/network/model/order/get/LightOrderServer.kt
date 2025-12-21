package com.bunbeauty.shared.data.network.model.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LightOrderServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("code")
    val code: String,
    @SerialName("status")
    val status: String,
    @SerialName("time")
    val time: Long,
    @SerialName("timeZone")
    val timeZone: String,
)
