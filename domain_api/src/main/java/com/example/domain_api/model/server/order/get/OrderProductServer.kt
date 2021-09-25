package com.example.domain_api.model.server.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderProductServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("count")
    val count: Int,

    @SerialName("name")
    val name: String,

    @SerialName("cost")
    val cost: Int,

    @SerialName("discountCost")
    val discountCost: Int?,

    @SerialName("weight")
    val weight: Int?,

    @SerialName("description")
    val description: String,

    @SerialName("comboDescription")
    val comboDescription: String?,

    @SerialName("photoLink")
    val photoLink: String,

    @SerialName("barcode")
    val barcode: Int?,

    @SerialName("orderUuid")
    val orderUuid: String,
)
