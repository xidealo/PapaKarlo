package com.example.domain_api.model.server.profile.get

import com.example.domain_api.model.server.AddressServer
import com.example.domain_api.model.server.order.get.OrderServer
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
    val orders: List<OrderServer>
)
