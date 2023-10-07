package com.bunbeauty.shared.domain.model

interface AuthResponseNew {

    data class Success(
        val token: String,
        val userUuid: String,
    ): AuthResponseNew

    enum class Error: AuthResponseNew {
        NO_ATTEMPTS_ERROR,
        INVALID_CODE_ERROR,
        AUTH_SESSION_TIMEOUT_ERROR,
        SOMETHING_WENT_WRONG_ERROR,
    }
}