package com.bunbeauty.shared.domain.feature.cart.di

import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetCartProductCountUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import com.bunbeauty.shared.domain.feature.cart.GetDeliveryCostUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import org.koin.dsl.module

internal fun cartModule() = module {
    factory {
        ObserveCartUseCase(
            cartProductRepo = get(),
            getNewTotalCostUseCase = get()
        )
    }
    factory {
        AddCartProductUseCase(
            getCartProductCountUseCase = get(),
            cartProductRepo = get(),
            cartProductAdditionRepository = get(),
            additionRepository = get(),
            areAdditionsEqualUseCase = get(),
            additionGroupRepository = get(),
            getAdditionPriorityUseCase = get()
        )
    }
    factory {
        GetRecommendationsUseCase(
            recommendationRepository = get(),
            cartProductRepo = get(),
            getMenuProductListUseCase = get()
        )
    }
    factory {
        RemoveCartProductUseCase(
            cartProductRepo = get(),
            cartProductAdditionRepository = get()
        )
    }
    factory {
        EditCartProductUseCase(
            cartProductRepo = get(),
            cartProductAdditionRepository = get(),
            areAdditionsEqualUseCase = get()
        )
    }
    factory {
        IncreaseCartProductCountUseCase(
            getCartProductCountUseCase = get(),
            cartProductRepo = get(),
        )
    }
    factory {
        GetCartProductCountUseCase(
            cartProductRepo = get(),
        )
    }
    factory {
        GetMotivationUseCase(
            getCurrentUserAddressUseCase = get(),
        )
    }
    factory {
        GetDeliveryCostUseCase(
            getCurrentUserAddressUseCase = get(),
        )
    }
}
