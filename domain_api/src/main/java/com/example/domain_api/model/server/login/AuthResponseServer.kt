package com.example.domain_api.model.server.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseServer(

    @SerialName("token")
    val token: String,

    )