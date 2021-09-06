package com.bunbeauty.domain.model.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryServer(
    @SerialName("uuid")
    val uuid:String,
    @SerialName("cost")
    val cost: Int,
    @SerialName("forFree")
    val forFree: Int
)