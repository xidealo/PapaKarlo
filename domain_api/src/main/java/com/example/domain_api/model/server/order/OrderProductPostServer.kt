package com.example.domain_api.model.server.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderProductPostServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("count")
    val count: Int,

    @SerialName("menuProductUuid")
    val menuProductUuid: String
)
