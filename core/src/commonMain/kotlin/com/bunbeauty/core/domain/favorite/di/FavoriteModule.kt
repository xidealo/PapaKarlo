package com.bunbeauty.core.domain.favorite.di

import com.bunbeauty.core.domain.favorite.IsProductFavoriteUseCase
import com.bunbeauty.core.domain.favorite.LoadFavoritesUseCase
import com.bunbeauty.core.domain.favorite.ToggleFavoriteUseCase
import org.koin.dsl.module

fun favoriteModule() =
    module {
        factory {
            LoadFavoritesUseCase(
                favoriteRepo = get(),
                userRepo = get(),
            )
        }
        factory {
            IsProductFavoriteUseCase(
                favoriteRepo = get(),
                userRepo = get(),
            )
        }
        factory {
            ToggleFavoriteUseCase(
                favoriteRepo = get(),
                userRepo = get(),
            )
        }
    }
