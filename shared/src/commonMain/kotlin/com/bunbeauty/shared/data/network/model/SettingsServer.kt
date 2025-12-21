package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingsServer(
    @SerialName("uuid")
    val userUuid: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("email")
    val email: String?,
)
