package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserAddressPostServer(
    @SerialName("street")
    val street: UserAddressStreetPostServer,
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
    @SerialName("isVisible")
    val isVisible: Boolean,
    @SerialName("cityUuid")
    val cityUuid: String,
)

@Serializable
class UserAddressStreetPostServer(
    @SerialName("fiasId")
    val fiasId: String,
    @SerialName("name")
    val name: String,
)
