package com.example.domain_api.model.server.profile.patch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserServer(
    @SerialName("email")
    val email: String,
)