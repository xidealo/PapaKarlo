package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressServer(

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

    @SerialName("minOrderCost")
    val minOrderCost: Int?,

    @SerialName("normalDeliveryCost")
    val normalDeliveryCost: Int,

    @SerialName("forLowDeliveryCost")
    val forLowDeliveryCost: Int?,

    @SerialName("lowDeliveryCost")
    val lowDeliveryCost: Int?,

    @SerialName("userUuid")
    val userUuid: String,

    @SerialName("cityUuid")
    val cityUuid: String,

    @SerialName("cafeUuid")
    val cafeUuid: String
)
