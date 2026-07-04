package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.Favorite

interface FavoriteRepo {
    suspend fun getFavoriteList(): List<Favorite>

    suspend fun isFavorite(menuProductUuid: String): Boolean

    suspend fun addFavorite(menuProductUuid: String)

    suspend fun removeFavorite(menuProductUuid: String)

    suspend fun clearCache()
}
