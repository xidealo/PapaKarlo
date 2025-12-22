package com.bunbeauty.shared.data.network.model.profile.get

import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("email")
    val email: String?,
    @SerialName("addresses")
    val addresses: List<AddressServer>,
    @SerialName("orders")
    val orders: List<OrderServer>,
)
