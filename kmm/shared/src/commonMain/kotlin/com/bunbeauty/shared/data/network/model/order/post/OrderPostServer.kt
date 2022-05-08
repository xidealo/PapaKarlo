package com.bunbeauty.shared.data.network.model.order.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderPostServer(

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("comment")
    val comment: String?,

    @SerialName("addressDescription")
    val addressDescription: String,

    @SerialName("deferredTime")
    val deferredTime: Long?,

    @SerialName("addressUuid")
    val addressUuid: String?,

    @SerialName("cafeUuid")
    val cafeUuid: String?,

    @SerialName("orderProducts")
    val orderProducts: List<OrderProductPostServer>,
)
