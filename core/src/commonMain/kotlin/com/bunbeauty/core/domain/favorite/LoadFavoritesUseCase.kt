package com.bunbeauty.core.domain.favorite

import com.bunbeauty.core.domain.repo.FavoriteRepo
import com.bunbeauty.core.domain.repo.UserRepo

class LoadFavoritesUseCase(
    private val favoriteRepo: FavoriteRepo,
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke() {
        if (userRepo.getToken() != null) {
            favoriteRepo.getFavoriteList()
        }
    }
}
