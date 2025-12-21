package com.bunbeauty.shared.data.network.model.profile.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePostServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String,
)
