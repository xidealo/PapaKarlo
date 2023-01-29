package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.order.*
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
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
        GetMinTimeUseCase(dateTimeUtil = get())
    }
    factory {
        DisableUserUseCase(
            userRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        CreateOrderUseCase(
            dataStoreRepo = get(),
            cartProductRepo = get(),
            dateTimeUtil = get(),
            orderRepo = get(),
        )
    }
    factory {
        ObserveLastOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
            lightOrderMapper = get(),
        )
    }
    factory {
        ObserveSettingsUseCase(
            settingsRepository = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        UpdateEmailUseCase(
            settingsRepository = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetCityListUseCase(
            cityRepo = get()
        )
    }
    factory {
        GetSelectedCityTimeZoneUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        GetSelectedCityUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        ObserveSelectedCityUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        SaveSelectedCityUseCase(
            dataStoreRepo = get()
        )
    }
    factory {
        StopObserveOrdersUseCase(
            orderRepo = get()
        )
    }
    factory {
        ObserveOrderListUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
            lightOrderMapper = get(),
        )
    }
    factory {
        ObserveOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
        )
    }
}