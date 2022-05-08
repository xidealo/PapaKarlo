package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAddressPostServer(

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

    @SerialName("isVisible")
    val isVisible: Boolean,
)
