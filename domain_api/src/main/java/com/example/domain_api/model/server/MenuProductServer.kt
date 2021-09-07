package com.example.domain_api.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuProductServer(
    @SerialName("uuid")
    val uuid: String,

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

    @SerialName("productCode")
    val productCode: String,

    @SerialName("barcode")
    val barcode: Int?,

    @SerialName("visible")
    val visible: Boolean
)