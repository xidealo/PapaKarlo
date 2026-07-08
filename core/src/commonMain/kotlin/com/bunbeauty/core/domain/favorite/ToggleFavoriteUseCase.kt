package com.bunbeauty.core.domain.favorite

import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.core.domain.repo.FavoriteRepo
import com.bunbeauty.core.domain.repo.UserRepo

class ToggleFavoriteUseCase(
    private val favoriteRepo: FavoriteRepo,
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke(menuProductUuid: String): Boolean {
        if (userRepo.getToken() == null) {
            throw NoTokenException()
        }

        val isFavorite = favoriteRepo.isFavorite(menuProductUuid = menuProductUuid)
        if (isFavorite) {
            favoriteRepo.removeFavorite(menuProductUuid = menuProductUuid)
            return false
        }

        favoriteRepo.addFavorite(menuProductUuid = menuProductUuid)
        return true
    }
}
