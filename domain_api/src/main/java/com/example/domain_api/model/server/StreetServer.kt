package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreetServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("cityUuid")
    val cityUuid: String,
)
