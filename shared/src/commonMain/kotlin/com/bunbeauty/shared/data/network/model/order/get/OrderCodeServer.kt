package com.bunbeauty.shared.data.network.model.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OrderCodeServer(
    @SerialName("code")
    val code: String
)
