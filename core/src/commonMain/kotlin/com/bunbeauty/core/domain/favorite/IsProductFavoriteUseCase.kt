package com.bunbeauty.core.domain.favorite

import com.bunbeauty.core.domain.repo.FavoriteRepo
import com.bunbeauty.core.domain.repo.UserRepo

class IsProductFavoriteUseCase(
    private val favoriteRepo: FavoriteRepo,
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke(menuProductUuid: String): Boolean {
        if (userRepo.getToken() == null) {
            return false
        }

        return favoriteRepo.isFavorite(menuProductUuid = menuProductUuid)
    }
}
