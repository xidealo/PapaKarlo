package com.bunbeauty.splash.di

import com.bunbeauty.splash.presentation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun splashFeatureModule() = module {
    viewModel {
        SplashViewModel(
            checkUpdateUseCase = get(),
            cityInteractor = get(),
            getIsOneCityUseCase = get(),
            saveOneCityUseCase = get(),
        )
    }
}