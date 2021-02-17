package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("street")
    val street: StreetServer,

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

    @SerialName("userUuid")
    val userUuid: String,
)
