package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UpdateNotificationTokenRequest(
    @SerialName("token")
    val token: String,
)
