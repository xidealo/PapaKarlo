package com.bunbeauty.shared.domain.feature.menu.di

import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.menu.di.menuFeatureModule
import org.koin.dsl.module

internal fun menuModule() =
    module {
        factory {
            AddMenuProductUseCase(
                getCartProductCountUseCase = get(),
                cartProductRepo = get(),
            )
        }
    }
