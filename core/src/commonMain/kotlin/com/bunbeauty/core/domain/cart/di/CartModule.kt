package com.bunbeauty.core.domain.cart.di

import com.bunbeauty.core.domain.cart.AddCartProductUseCase
import com.bunbeauty.core.domain.cart.EditCartProductUseCase
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.GetCartProductCountUseCaseImpl
import com.bunbeauty.core.domain.cart.GetDeliveryCostFlowUseCase
import com.bunbeauty.core.domain.cart.GetDeliveryCostFlowUseCaseImpl
import com.bunbeauty.core.domain.cart.GetRecommendationsUseCase
import com.bunbeauty.core.domain.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.core.domain.cart.RemoveCartProductUseCase
import com.bunbeauty.core.domain.motivation.GetMotivationUseCase
import org.koin.dsl.module

fun cartModule() =
    module {
        factory {
            ObserveCartUseCase(
                cartProductRepo = get(),
                getNewTotalCostUseCase = get(),
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
                getAdditionPriorityUseCase = get(),
            )
        }
        factory {
            GetRecommendationsUseCase(
                recommendationRepository = get(),
                cartProductRepo = get(),
                getMenuProductListUseCase = get(),
            )
        }
        factory {
            RemoveCartProductUseCase(
                cartProductRepo = get(),
                cartProductAdditionRepository = get(),
            )
        }
        factory {
            EditCartProductUseCase(
                cartProductRepo = get(),
                cartProductAdditionRepository = get(),
                areAdditionsEqualUseCase = get(),
            )
        }
        factory {
            IncreaseCartProductCountUseCase(
                getCartProductCountUseCase = get(),
                cartProductRepo = get(),
            )
        }
        factory<GetCartProductCountUseCase> {
            GetCartProductCountUseCaseImpl(
                cartProductRepo = get(),
            )
        }
        factory {
            GetMotivationUseCase(
                getCurrentUserAddressUseCase = get(),
                getUserAddressListUseCase = get(),
            )
        }
        factory<GetDeliveryCostFlowUseCase> {
            GetDeliveryCostFlowUseCaseImpl(
                getCurrentUserAddressFlowUseCase = get(),
            )
        }
    }
