package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String?,
    @SerialName("addressList")
    val addressList: List<UserAddressServer>
)
