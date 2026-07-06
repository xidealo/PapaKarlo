package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.core.domain.repo.FavoriteRepo
import com.bunbeauty.core.domain.repo.UserRepo
import com.bunbeauty.core.extension.getNullableResult
import com.bunbeauty.core.extension.map
import com.bunbeauty.core.model.Favorite
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.PostFavoriteServer
import com.bunbeauty.shared.data.network.model.toFavorite

class FavoriteRepository(
    private val networkConnector: NetworkConnector,
    private val userRepo: UserRepo,
) : CacheListRepository<Favorite>(),
    FavoriteRepo {
    override val tag: String = "FAVORITE_TAG"

    override suspend fun getFavoriteList(): List<Favorite> {
        val token = userRepo.getToken() ?: return emptyList()

        return getCacheOrListData(
            onApiRequest = {
                networkConnector.getFavoriteList(token = token)
            },
            onLocalRequest = {
                emptyList()
            },
            onSaveLocally = { },
            serverToDomainModel = { favoriteServer ->
                favoriteServer.toFavorite()
            },
        )
    }

    override suspend fun isFavorite(menuProductUuid: String): Boolean =
        getFavoriteList().any { favorite ->
            favorite.menuProductUuid == menuProductUuid
        }

    override suspend fun addFavorite(menuProductUuid: String) {
        val token = userRepo.getToken() ?: throw NoTokenException()

        networkConnector
            .postFavorite(
                token = token,
                body = PostFavoriteServer(menuProductUuid = menuProductUuid),
            ).getNullableResult(
                onError = { apiError ->
                    throw IllegalStateException(apiError.message)
                },
                onSuccess = { favoriteServer ->
                    updateCache { favoriteList ->
                        val favorite = favoriteServer.toFavorite()
                        val currentList = favoriteList ?: emptyList()
                        if (currentList.any { item -> item.menuProductUuid == menuProductUuid }) {
                            currentList
                        } else {
                            currentList + favorite
                        }
                    }
                },
            )
    }

    override suspend fun removeFavorite(menuProductUuid: String) {
        val token = userRepo.getToken() ?: throw NoTokenException()

        networkConnector
            .deleteFavorite(
                token = token,
                menuProductUuid = menuProductUuid,
            ).map(
                onError = { apiError ->
                    throw IllegalStateException(apiError.message)
                },
                onSuccess = {
                    updateCache { favoriteList ->
                        favoriteList?.filter { favorite ->
                            favorite.menuProductUuid != menuProductUuid
                        }
                    }
                },
            )
    }

    override suspend fun clearCache() {
        cache = null
    }
}
