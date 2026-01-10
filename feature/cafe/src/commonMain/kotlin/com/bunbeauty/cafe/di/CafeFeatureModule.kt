package com.bunbeauty.cafe.di

import com.bunbeauty.cafe.presentation.cafe_list.CafeListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun cafeFeatureModule() =
    module {
        viewModel {
            CafeListViewModel(
                cafeInteractor = get(),
                observeCafeWithOpenStateListUseCase = get(),
            )
        }
    }
