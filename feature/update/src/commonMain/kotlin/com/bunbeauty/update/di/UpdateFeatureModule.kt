package com.bunbeauty.update.di

import com.bunbeauty.update.presentation.UpdateViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun updateFeatureModule() = module {
    viewModel {
        UpdateViewModel(
            getLinkUseCase = get(),
        )
    }
}