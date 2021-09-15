package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OrderServer(

    @SerialName("uuid")
    val uuid: String,

    )
