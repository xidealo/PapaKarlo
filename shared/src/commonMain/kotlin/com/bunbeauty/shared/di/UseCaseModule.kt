package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.interactor.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.deferred_time.GetMinTimeUseCase
import org.koin.dsl.module

internal fun useCaseModule() = module {

    factory {
        GetSelectedUserAddressUseCase(
            userAddressRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetUserAddressListUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
    factory {
        GetSelectedCafeUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetCartTotalUseCase(
            cartProductRepo = get(),
            deliveryRepo = get(),
        )
    }
    factory {
        GetMinTimeUseCase(
            dateTimeUtil = get(),
            dataStoreRepo = get(),
        )
    }
}