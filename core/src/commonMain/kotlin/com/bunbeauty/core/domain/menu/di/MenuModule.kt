package com.bunbeauty.core.domain.menu.di

import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import org.koin.dsl.module

fun menuModule() =
    module {
        factory {
            AddMenuProductUseCase(
                getCartProductCountUseCase = get(),
                cartProductRepo = get(),
            )
        }
    }
