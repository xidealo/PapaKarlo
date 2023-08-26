package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import org.koin.dsl.module

internal fun cartUseCaseModule() = module {
    factory {
        ObserveCartUseCase(
            cartProductRepo = get()
        )
    }
    factory {
        AddCartProductUseCase(
            cartProductRepo = get()
        )
    }
    factory {
        RemoveCartProductUseCase(
            cartProductRepo = get()
        )
    }
}
