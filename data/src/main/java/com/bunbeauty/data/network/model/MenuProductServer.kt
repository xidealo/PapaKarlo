package com.bunbeauty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuProductServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("newPrice")
    val newPrice: Int,

    @SerialName("oldPrice")
    val oldPrice: Int?,

    @SerialName("utils")
    val utils: String?,

    @SerialName("nutrition")
    val nutrition: Int?,

    @SerialName("description")
    val description: String,

    @SerialName("comboDescription")
    val comboDescription: String?,

    @SerialName("photoLink")
    val photoLink: String,

    @SerialName("barcode")
    val barcode: Int?,

    @SerialName("categories")
    val categories: List<CategoryServer>,

    @SerialName("isVisible")
    val isVisible: Boolean
)