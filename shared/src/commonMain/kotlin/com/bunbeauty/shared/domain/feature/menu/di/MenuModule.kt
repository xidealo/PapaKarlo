package com.bunbeauty.shared.domain.feature.menu.di

import com.bunbeauty.shared.domain.feature.menu.AddMenuProductUseCase
import org.koin.dsl.module

internal fun menuModule() = module {
    factory {
        AddMenuProductUseCase(
            getCartProductCountUseCase = get(),
            cartProductRepo = get()
        )
    }
}
