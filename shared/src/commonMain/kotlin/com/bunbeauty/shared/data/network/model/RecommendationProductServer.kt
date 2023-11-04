package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationProductListServer(
    @SerialName("maxVisibleCount")
    val maxVisibleCount: Int,
    @SerialName("recommendationList")
    val recommendationList: List<RecommendationProductServer>,
)

@Serializable
data class RecommendationProductServer(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("menuProduct")
    val menuProduct: MenuProductServer,
    @SerialName("isVisible")
    val isVisible: Boolean,
)