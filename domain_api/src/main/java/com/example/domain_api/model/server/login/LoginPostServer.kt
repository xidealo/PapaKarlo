package com.example.domain_api.model.server.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPostServer(

    @SerialName("firebaseUuid")
    val firebaseUuid: String,

    @SerialName("phoneNumber")
    val phoneNumber: String,

    @SerialName("companyUuid")
    val companyUuid: String,
)