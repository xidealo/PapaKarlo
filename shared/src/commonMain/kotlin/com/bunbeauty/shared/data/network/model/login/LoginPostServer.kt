package com.bunbeauty.shared.data.network.model.login

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