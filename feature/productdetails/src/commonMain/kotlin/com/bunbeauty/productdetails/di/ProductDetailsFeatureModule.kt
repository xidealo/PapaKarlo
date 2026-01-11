package com.bunbeauty.productdetails.di

import com.bunbeauty.productdetails.presentation.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun productDetailsFeatureModule() =
    module {
        viewModel {
            ProductDetailsViewModel(
                getMenuProductUseCase = get(),
                observeCartUseCase = get(),
                addCartProductUseCase = get(),
                analyticService = get(),
                editCartProductUseCase = get(),
                getAdditionGroupsWithSelectedAdditionUseCase = get(),
                getSelectedAdditionsPriceUseCase = get(),
            )
        }
    }
