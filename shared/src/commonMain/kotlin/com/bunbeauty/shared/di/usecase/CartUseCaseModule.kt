package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import org.koin.dsl.module

internal fun cartUseCaseModule() = module {
    factory {
        ObserveCartUseCase(
            cartProductRepo = get(),
            getNewTotalCostUseCase = get()
        )
    }
    factory {
        AddCartProductUseCase(
            cartProductRepo = get(),
            cartProductAdditionRepository = get(),
            additionRepository = get()
        )
    }
    factory {
        GetRecommendationsUseCase(
            recommendationRepository = get(),
            cartProductInteractor = get(),
            menuProductRepository = get()
        )
    }
    factory {
        RemoveCartProductUseCase(
            cartProductRepo = get()
        )
    }
}
