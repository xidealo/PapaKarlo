package com.bunbeauty.shared.data.network.model

import com.bunbeauty.core.model.Favorite
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteServer(
    @SerialName("menuProductUuid")
    val menuProductUuid: String,
    @SerialName("createdAt")
    val createdAt: String,
)

@Serializable
data class PostFavoriteServer(
    @SerialName("menuProductUuid")
    val menuProductUuid: String,
)

fun FavoriteServer.toFavorite(): Favorite =
    Favorite(
        menuProductUuid = menuProductUuid,
        createdAtMillis = Instant.parse(createdAt).toEpochMilliseconds(),
    )
