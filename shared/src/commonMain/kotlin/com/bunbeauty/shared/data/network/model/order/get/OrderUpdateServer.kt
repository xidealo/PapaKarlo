package com.bunbeauty.shared.data.network.model.order.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OrderUpdateServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("status")
    val status: String

)