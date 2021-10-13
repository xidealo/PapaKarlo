package com.example.domain_api.model.server.profile.get

import com.example.domain_api.model.server.UserAddressServer
import com.example.domain_api.model.server.order.get.OrderServer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileServer(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("email")
    val email: String,

    @SerialName("addressList")
    val addressList: List<UserAddressServer>,

    @SerialName("userOrderList")
    val orderList: List<OrderServer>
)