package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.buildVersionQualifier
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCaseImpl
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCaseImpl
import com.bunbeauty.shared.domain.feature.link.GetLinkUseCase
import com.bunbeauty.core.domain.menu_product.GetMenuProductListUseCase
import com.bunbeauty.core.domain.menu_product.GetMenuProductListUseCaseImpl
import com.bunbeauty.core.domain.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckOneCityUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.feature.splash.SaveOneCityUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.core.domain.GetNewTotalCostUseCase
import com.bunbeauty.core.domain.GetNewTotalCostUseCaseImpl
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCaseImpl
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import org.koin.dsl.module

internal fun useCaseModules() =
    module {
        factory {
            GetCartTotalFlowUseCase(
                cartProductRepo = get(),
                getDiscountUseCase = get(),
                getNewTotalCostUseCase = get(),
                getOldTotalCostUseCase = get(),
                getDeliveryCostFlowUseCase = get(),
            )
        }
        factory<GetNewTotalCostUseCase> {
            GetNewTotalCostUseCaseImpl(
                getDiscountUseCase = get(),
                getCartProductAdditionsPriceUseCase = get(),
            )
        }
        factory<GetOldTotalCostUseCase> {
            GetOldTotalCostUseCaseImpl(
                getCartProductAdditionsPriceUseCase = get(),
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
        factory<GetSelectedCityTimeZoneUseCase> {
            GetSelectedCityTimeZoneUseCaseImpl(
                cityRepo = get(),
                dataStoreRepo = get(),
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
                menuProductRepo = get(),
            )
        }
        factory<GetMenuProductListUseCase> {
            GetMenuProductListUseCaseImpl(
                menuProductRepo = get(),
            )
        }
        factory {
            GetFilteredStreetListUseCase()
        }
        factory {
            GetLinkUseCase(
                linkRepo = get(),
            )
        }
        factory {
            SubscribeToNotificationUseCase()
        }
        factory<GetDiscountUseCase> {
            GetDiscountUseCaseImpl(
                discountRepository = get(),
                orderRepository = get(),
            )
        }
        factory {
            CheckUpdateUseCase(
                versionRepo = get(),
                buildVersion = get(buildVersionQualifier),
            )
        }
        factory {
            CheckOneCityUseCase(cityRepo = get())
        }
        factory {
            SaveOneCityUseCase(
                cityRepo = get(),
                dataStoreRepo = get(),
            )
        }
    }
