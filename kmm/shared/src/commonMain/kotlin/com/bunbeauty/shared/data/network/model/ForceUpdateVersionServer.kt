package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForceUpdateVersionServer(
    @SerialName("version")
    val version: Int
)