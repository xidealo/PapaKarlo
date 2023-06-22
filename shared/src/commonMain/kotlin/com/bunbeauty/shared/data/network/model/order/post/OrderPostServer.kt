package com.bunbeauty.shared.data.network.model.order.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OrderPostServer(

    @SerialName("isDelivery")
    val isDelivery: Boolean,

    @SerialName("address")
    val address: OrderAddressPostServer,

    @SerialName("comment")
    val comment: String?,

    @SerialName("deferredTime")
    val deferredTime: Long?,

    @SerialName("orderProducts")
    val orderProducts: List<OrderProductPostServer>,

    @SerialName("paymentMethod")
    val paymentMethod: String?,
)

@Serializable
class OrderAddressPostServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("description")
    val description: String?,

    @SerialName("street")
    val street: String?,

    @SerialName("house")
    val house: String?,

    @SerialName("flat")
    val flat: String?,

    @SerialName("entrance")
    val entrance: String?,

    @SerialName("floor")
    val floor: String?,

    @SerialName("comment")
    val comment: String?,
)

@Serializable
class OrderProductPostServer(

    @SerialName("count")
    val count: Int,

    @SerialName("menuProductUuid")
    val menuProductUuid: String,
)
