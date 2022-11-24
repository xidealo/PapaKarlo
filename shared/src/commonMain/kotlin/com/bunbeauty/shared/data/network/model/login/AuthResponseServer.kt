package com.bunbeauty.shared.data.network.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseServer(
    @SerialName("token")
    val token: String,
    @SerialName("userUuid")
    val userUuid: String,
)