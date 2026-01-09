package com.bunbeauty.shared.di

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.di.analyticModule
import com.bunbeauty.shared.data.CompanyUuidProvider
import com.bunbeauty.shared.data.di.dataMapperModule
import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.providerModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.di.storageModule
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.di.usecase.additionUseCaseModule
import com.bunbeauty.shared.di.usecase.authUseCaseModule
import com.bunbeauty.shared.di.usecase.cityUseCaseModule
import com.bunbeauty.shared.di.usecase.orderUseCaseModule
import com.bunbeauty.shared.di.usecase.paymentUseCaseModule
import com.bunbeauty.shared.di.usecase.useCaseModules
import com.bunbeauty.shared.di.usecase.userAddressUseCaseModule
import com.bunbeauty.shared.domain.feature.addition.GetAdditionGroupsWithSelectedAdditionUseCase
import com.bunbeauty.shared.domain.feature.addition.GetPriceOfSelectedAdditionsUseCase
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressWithCityUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import com.bunbeauty.shared.domain.feature.address.di.addressModule
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.CheckPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.ResendCodeUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetAdditionalUtensilsUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetDeferredTimeHintUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetWorkloadCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.HasOpenedCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsDeliveryEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.cafe.di.cafeModule
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.di.cartModule
import com.bunbeauty.core.domain.city.GetCityListUseCase
import com.bunbeauty.core.domain.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.core.domain.city.ObserveSelectedCityUseCase
import com.bunbeauty.core.domain.city.SaveSelectedCityUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.link.GetLinkUseCase
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.shared.domain.feature.menu.di.menuModule
import com.bunbeauty.core.domain.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.notification.UpdateNotificationUseCase
import com.bunbeauty.core.domain.order.CreateOrderUseCase
import com.bunbeauty.core.domain.order.GetExtendedCommentUseCase
import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveOrderListUseCase
import com.bunbeauty.core.domain.order.ObserveOrderUseCase
import com.bunbeauty.core.domain.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectedPaymentMethodUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.core.domain.settings.ObserveSettingsUseCase
import com.bunbeauty.core.domain.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckOneCityUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.feature.splash.SaveOneCityUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.core.domain.city.ICityInteractor
import com.bunbeauty.core.domain.menu_product.IMenuProductInteractor
import com.bunbeauty.menu.di.menuFeatureModule
import com.bunbeauty.profile.di.profileFeatureModule
import com.bunbeauty.core.domain.user.IUserInteractor
import com.bunbeauty.core.domain.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            networkModule(),
            databaseModule(),
            providerModule(),
            dataMapperModule(),
            repositoryModule(),
            storageModule(),
            interactorModule(),
            utilModule(),
            platformModule(),
            domainMapperModule(),
            cityUseCaseModule(),
            userAddressUseCaseModule(),
            orderUseCaseModule(),
            cartModule(),
            cafeModule(),
            paymentUseCaseModule(),
            authUseCaseModule(),
            useCaseModules(),
            analyticModule(),
            additionUseCaseModule(),
            menuModule(),
            menuFeatureModule(),
            addressModule(),
            viewModelModule(),
            profileFeatureModule(),
        )
    }

/**
 * Function for init in IOS
 * */
fun initKoin() =
    startKoin {
        modules(
            databaseModule(),
            platformModule(),
            viewModelModule(),
            providerModule(),
            networkModule(),
            dataMapperModule(),
            repositoryModule(),
            storageModule(),
            interactorModule(),
            utilModule(),
            domainMapperModule(),
            cityUseCaseModule(),
            userAddressUseCaseModule(),
            orderUseCaseModule(),
            cartModule(),
            cafeModule(),
            paymentUseCaseModule(),
            authUseCaseModule(),
            useCaseModules(),
            analyticModule(),
            additionUseCaseModule(),
            menuModule(),
            menuFeatureModule(),
            addressModule(),
            profileFeatureModule(),
        )
    }

class IosComponent : KoinComponent {
    // Interactors
    fun provideCityInteractor(): ICityInteractor = get()

    fun provideMenuInteractor(): IMenuProductInteractor = get()

    fun provideCafeInteractor(): ICafeInteractor = get()

    fun provideCartProductInteractor(): ICartProductInteractor = get()

    fun provideIUserInteractor(): IUserInteractor = get()

    // Use cases
    fun provideDisableUserUseCase(): DisableUserUseCase = get()

    fun provideGetUserAddressListUseCase(): GetUserAddressListUseCase = get()

    fun provideGetSelectableUserAddressListUseCase(): GetSelectableUserAddressListUseCase = get()

    fun provideGetCafeListUseCase(): GetCafeListUseCase = get()

    fun provideGetSelectableCafeListUseCase(): GetSelectableCafeListUseCase = get()

    fun provideGetCartTotalUseCase(): GetCartTotalFlowUseCase = get()

    fun provideGetMinTimeUseCase(): GetMinTimeUseCase = get()

    fun provideCreateOrderUseCase(): CreateOrderUseCase = get()

    fun provideGetSelectedCityTimeZoneUseCase(): GetSelectedCityTimeZoneUseCase = get()

    fun provideObserveSettingsUseCase(): ObserveSettingsUseCase = get()

    fun provideObserveSelectedCityUseCase(): ObserveSelectedCityUseCase = get()

    fun provideUpdateEmailUseCase(): UpdateEmailUseCase = get()

    fun provideGetCityListUseCase(): GetCityListUseCase = get()

    fun provideSaveSelectedCityUseCase(): SaveSelectedCityUseCase = get()

    fun provideObserveLastOrderUseCase(): ObserveLastOrderUseCase = get()

    fun provideStopObserveOrdersUseCase(): StopObserveOrdersUseCase = get()

    fun provideObserveOrderListUseCase(): ObserveOrderListUseCase = get()

    fun provideObserveOrderUseCase(): ObserveOrderUseCase = get()

    fun provideCreateAddressUseCase(): CreateAddressUseCase = get()

    fun provideSaveSelectedUserAddressUseCase(): SaveSelectedUserAddressUseCase = get()

    fun provideGetLastOrderUseCase(): GetLastOrderUseCase = get()

    fun provideGetFilteredStreetListUseCase(): GetFilteredStreetListUseCase = get()

    fun provideObserveCartUseCase(): ObserveCartUseCase = get()

    fun provideRemoveCartProductUseCase(): RemoveCartProductUseCase = get()

    fun provideGetLinkListUseCase(): GetLinkListUseCase = get()

    fun provideGetLinkUseCase(): GetLinkUseCase = get()

    fun provideGetPaymentMethodListUseCase(): GetPaymentMethodListUseCase = get()

    fun provideSubscribeToNotificationUseCase(): SubscribeToNotificationUseCase = get()

    fun provideGetSelectablePaymentMethodListUseCase(): GetSelectablePaymentMethodListUseCase =
        get()

    fun provideAddCartProductUseCase(): AddCartProductUseCase = get()

    fun provideGetDiscountUseCase(): GetDiscountUseCase = get()

    fun provideSavePaymentMethodUseCase(): SavePaymentMethodUseCase = get()

    fun provideRequestCodeUseCase(): RequestCodeUseCase = get()

    fun provideFormatPhoneNumberUseCase(): FormatPhoneNumberUseCase = get()

    fun provideGetPhoneNumberCursorPositionUseCase(): GetPhoneNumberCursorPositionUseCase = get()

    fun provideCheckCodeUseCase(): CheckCodeUseCase = get()

    fun provideResendCodeUseCase(): ResendCodeUseCase = get()

    fun provideCheckPhoneNumberUseCase(): CheckPhoneNumberUseCase = get()

    fun provideGetRecommendationsUseCase(): GetRecommendationsUseCase = get()

    fun provideGetMenuProductByUuidUseCase(): GetMenuProductUseCase = get()

    fun provideGetSuggestionsUseCase(): GetSuggestionsUseCase = get()

    fun provideIncreaseCartProductCountUseCase(): IncreaseCartProductCountUseCase = get()

    fun provideAddMenuProductUseCase(): AddMenuProductUseCase = get()

    fun provideEditCartProductUseCase(): EditCartProductUseCase = get()

    fun provideGetAdditionGroupsWithSelectedAdditionUseCase(): GetAdditionGroupsWithSelectedAdditionUseCase =
        get()

    fun provideGetPriceOfSelectedAdditionsUseCase(): GetPriceOfSelectedAdditionsUseCase = get()

    fun provideObserveCafeWithOpenStateListUseCase(): ObserveCafeWithOpenStateListUseCase = get()

    fun provideGetMotivationUseCaseUseCase(): GetMotivationUseCase = get()

    fun provideIsOrderAvailableUseCase(): IsOrderAvailableUseCase = get()

    fun provideCheckUpdateUseCase(): CheckUpdateUseCase = get()

    fun provideCheckOneCityUseCase(): CheckOneCityUseCase = get()

    fun provideSaveOneCityUseCase(): SaveOneCityUseCase = get()

    fun provideGetCurrentUserAddressUseCase(): GetCurrentUserAddressUseCase = get()

    fun provideIsPickupEnabledFromCafeUseCase(): IsPickupEnabledFromCafeUseCase = get()

    fun provideHasOpenedCafeUseCase(): HasOpenedCafeUseCase = get()

    fun provideGetWorkloadCafeUseCase(): GetWorkloadCafeUseCase = get()

    fun provideIsDeliveryEnabledFromCafeUseCase(): IsDeliveryEnabledFromCafeUseCase = get()

    fun provideUpdateNotificationUseCase(): UpdateNotificationUseCase = get()

    fun provideGetCurrentUserAddressWithCityUseCase(): GetCurrentUserAddressWithCityUseCase = get()

    fun provideGetExtendedCommentUseCase(): GetExtendedCommentUseCase = get()

    fun provideGetSelectedPaymentMethodUseCase(): GetSelectedPaymentMethodUseCase = get()

    fun provideGetAdditionalUtensilsUseCase(): GetAdditionalUtensilsUseCase = get()

    fun provideGetDeferredTimeHintUseCase(): GetDeferredTimeHintUseCase = get()

    // Mapper
    fun provideUserAddressMapper(): UserAddressMapper = get()

    // Other
    fun provideApiRepo(): NetworkConnector = get()

    fun provideAnalyticService(): AnalyticService = get()

    fun provideCompanyUuidProvider(): CompanyUuidProvider = get()
}
