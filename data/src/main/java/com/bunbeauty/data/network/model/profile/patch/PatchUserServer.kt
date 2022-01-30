package com.bunbeauty.data.network.model.profile.patch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserServer(
    @SerialName("email")
    val email: String,
)