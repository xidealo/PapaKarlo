package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddressServer(
    @SerialName("uuid")
    val uuid: String = UUID.randomUUID().toString(),
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
    @SerialName("streetUuid")
    val streetUuid: String,
    @SerialName("userUuid")
    val userUuid: String?,
)
