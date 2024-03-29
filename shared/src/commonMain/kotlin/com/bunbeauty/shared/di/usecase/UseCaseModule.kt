package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import org.koin.dsl.module

internal fun useCaseModules() = module {
    factory {
        GetCartTotalUseCase(
            cartProductRepo = get(),
            deliveryRepo = get(),
            getDiscountUseCase = get(),
            getNewTotalCostUseCase = get(),
            getOldTotalCostUseCase = get()
        )
    }

    factory {
        GetNewTotalCostUseCase(
            getDiscountUseCase = get(),
            getCartProductAdditionsPriceUseCase = get()
        )
    }
    factory {
        GetOldTotalCostUseCase(
            getCartProductAdditionsPriceUseCase = get()
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
        GetSelectedCityTimeZoneUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        CreateAddressUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
    factory {
        GetMenuProductUseCase(
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
    factory {
        GetDiscountUseCase(
            discountRepository = get(),
            orderRepository = get(),
            dataStoreRepo = get(),
        )
    }

}