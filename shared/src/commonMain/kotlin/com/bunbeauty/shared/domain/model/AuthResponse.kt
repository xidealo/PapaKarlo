package com.bunbeauty.shared.domain.model

@Deprecated("Use AuthResponseNew")
class AuthResponse(
    val token: String,
    val userUuid: String,
)