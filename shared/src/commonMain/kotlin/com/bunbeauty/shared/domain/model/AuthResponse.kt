package com.bunbeauty.shared.domain.model

data class AuthResponse(
    val token: String,
    val userUuid: String,
)