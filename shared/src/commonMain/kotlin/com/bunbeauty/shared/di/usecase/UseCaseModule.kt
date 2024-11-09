package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCaseImpl
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCaseImpl
import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.menuproduct.GetMenuProductListUseCase
import com.bunbeauty.shared.domain.feature.menuproduct.GetMenuProductListUseCaseImpl
import com.bunbeauty.shared.domain.feature.menuproduct.GetMenuProductUseCase
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckOneCityUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.feature.splash.SaveOneCityUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCaseImpl
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCaseImpl
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import org.koin.dsl.module

internal fun useCaseModules() = module {
    factory {
        GetCartTotalFlowUseCase(
            cartProductRepo = get(),
            getDiscountUseCase = get(),
            getNewTotalCostUseCase = get(),
            getOldTotalCostUseCase = get(),
            getDeliveryCostFlowUseCase = get()
        )
    }
    factory<GetNewTotalCostUseCase> {
        GetNewTotalCostUseCaseImpl(
            getDiscountUseCase = get(),
            getCartProductAdditionsPriceUseCase = get()
        )
    }
    factory<GetOldTotalCostUseCase> {
        GetOldTotalCostUseCaseImpl(
            getCartProductAdditionsPriceUseCase = get()
        )
    }

    factory {
        GetMinTimeUseCase(dateTimeUtil = get())
    }
    factory {
        DisableUserUseCase(
            userRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        ObserveSettingsUseCase(
            settingsRepository = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        UpdateEmailUseCase(
            settingsRepository = get(),
            dataStoreRepo = get()
        )
    }
    factory<GetSelectedCityTimeZoneUseCase> {
        GetSelectedCityTimeZoneUseCaseImpl(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        CreateAddressUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get()
        )
    }
    factory {
        GetMenuProductUseCase(
            menuProductRepo = get()
        )
    }
    factory<GetMenuProductListUseCase> {
        GetMenuProductListUseCaseImpl(
            menuProductRepo = get()
        )
    }
    factory {
        GetFilteredStreetListUseCase()
    }

    factory {
        GetLinkListUseCase(
            linkRepo = get()
        )
    }
    factory {
        SubscribeToNotificationUseCase()
    }
    factory<GetDiscountUseCase> {
        GetDiscountUseCaseImpl(
            discountRepository = get(),
            orderRepository = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        CheckUpdateUseCase(versionRepo = get())
    }
    factory {
        CheckOneCityUseCase(cityRepo = get())
    }
    factory {
        SaveOneCityUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
}
