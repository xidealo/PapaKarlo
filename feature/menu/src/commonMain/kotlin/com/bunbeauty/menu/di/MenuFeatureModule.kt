package com.bunbeauty.menu.di

import com.bunbeauty.menu.presentation.MenuViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun menuFeatureModule() = module {
    viewModel {
        MenuViewModel(
            menuProductInteractor = get(),
            observeCartUseCase = get(),
            addMenuProductUseCase = get(),
            getDiscountUseCase = get(),
            analyticService = get(),
        )
    }
}