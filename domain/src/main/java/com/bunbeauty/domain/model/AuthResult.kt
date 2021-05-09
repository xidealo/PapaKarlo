package com.bunbeauty.domain.model

sealed class AuthResult {

    object AuthSuccess : AuthResult()
    class AuthError(val errorMessage: String) : AuthResult()
}
