package com.bunbeauty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryServer(
    @SerialName("cost")
    val cost: Int,
    @SerialName("forFree")
    val forFree: Int
)