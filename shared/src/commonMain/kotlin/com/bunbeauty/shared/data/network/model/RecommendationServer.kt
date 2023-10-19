package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("menuProduct")
    val menuProduct: MenuProductServer,
)