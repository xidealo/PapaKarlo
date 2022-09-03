package com.bunbeauty.shared.data.network.model.order.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderProductPostServer(

    @SerialName("count")
    val count: Int,

    @SerialName("menuProductUuid")
    val menuProductUuid: String
)
