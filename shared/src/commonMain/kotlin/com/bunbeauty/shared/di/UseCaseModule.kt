package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductByUuidUseCase
import com.bunbeauty.shared.domain.feature.order.*
import com.bunbeauty.shared.domain.feature.payment.GetPaymentInfoUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.use_case.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.use_case.street.GetStreetsUseCase
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.address.*
import com.bunbeauty.shared.domain.use_case.cafe.GetSelectableCafeListUseCase
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
        GetSelectableUserAddressListUseCase(
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
        GetSelectableCafeListUseCase(
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
    factory {
        GetStreetsUseCase(
            streetRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        CreateAddressUseCase(
            streetRepo = get(),
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
    factory {
        SaveSelectedUserAddressUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
    factory {
        GetLastOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get()
        )
    }
    factory {
        GetMenuProductByUuidUseCase(
            menuProductRepo = get()
        )
    }
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
        GetFilteredStreetListUseCase()
    }
    factory {
        GetPaymentInfoUseCase()
    }
}