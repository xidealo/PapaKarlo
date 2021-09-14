package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAddressServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("street")
    val street: String,

    @SerialName("house")
    val house: String,

    @SerialName("flat")
    val flat: String?,

    @SerialName("entrance")
    val entrance: String?,

    @SerialName("floor")
    val floor: String?,

    @SerialName("comment")
    val comment: String?,

    @SerialName("profileUuid")
    val userUuid: String,

    @SerialName("streetUuid")
    val streetUuid: String,
)
