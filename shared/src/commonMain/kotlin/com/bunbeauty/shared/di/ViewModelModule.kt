package com.bunbeauty.shared.di

import com.bunbeauty.shared.presentation.MainViewModel
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.createorder.presentation.createorder.CreateOrderViewModel
import com.bunbeauty.profile.presentation.selectcity.SelectCityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() =
    module {
        viewModel {
            MainViewModel(
                isOrderAvailableUseCase = get(),
                networkUtil = get(),
            )
        }
        viewModel {
            ConsumerCartViewModel(
                userInteractor = get(),
                cartProductInteractor = get(),
                increaseCartProductCountUseCase = get(),
                addMenuProductUseCase = get(),
                removeCartProductUseCase = get(),
                getRecommendationsUseCase = get(),
                getMotivationUseCase = get(),
                analyticService = get(),
                isOrderAvailableUseCase = get(),
            )
        }

        viewModel {
            SelectCityViewModel(
                cityInteractor = get(),
            )
        }

    }
