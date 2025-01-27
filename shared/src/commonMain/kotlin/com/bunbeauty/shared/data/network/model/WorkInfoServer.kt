package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkInfoServer(
    @SerialName("workType")
    val workType: String
)
