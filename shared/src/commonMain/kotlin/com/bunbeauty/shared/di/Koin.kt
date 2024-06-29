package com.bunbeauty.shared.di

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.di.analyticModule
import com.bunbeauty.shared.data.CompanyUuidProvider
import com.bunbeauty.shared.data.di.dataMapperModule
import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.providerModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.di.usecase.additionUseCaseModule
import com.bunbeauty.shared.di.usecase.authUseCaseModule
import com.bunbeauty.shared.domain.feature.cafe.di.cafeModule
import com.bunbeauty.shared.domain.feature.cart.di.cartModule
import com.bunbeauty.shared.di.usecase.cityUseCaseModule
import com.bunbeauty.shared.di.usecase.orderUseCaseModule
import com.bunbeauty.shared.di.usecase.paymentUseCaseModule
import com.bunbeauty.shared.di.usecase.useCaseModules
import com.bunbeauty.shared.di.usecase.userAddressUseCaseModule
import com.bunbeauty.shared.domain.feature.addition.GetAdditionGroupsWithSelectedAdditionUseCase
import com.bunbeauty.shared.domain.feature.addition.GetPriceOfSelectedAdditionsUseCase
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.order.*
import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.feature.address.di.addressModule
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.CheckPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.ResendCodeUseCase
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.feature.menu.AddMenuProductUseCase
import com.bunbeauty.shared.domain.feature.menu.di.menuModule
import com.bunbeauty.shared.domain.feature.menuproduct.GetMenuProductUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.address.*
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        databaseModule(),
        providerModule(),
        networkModule(),
        dataMapperModule(),
        repositoryModule(),
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
        addressModule(),
    )
}
/**
* Function for init in IOS
* */
fun initKoin() = startKoin {
    modules(
        databaseModule(),
        platformModule(),
        providerModule(),
        networkModule(),
        dataMapperModule(),
        repositoryModule(),
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
        addressModule()
    )
}

class IosComponent : KoinComponent {
    // Interactors
    fun provideCityInteractor(): ICityInteractor = get()
    fun provideMenuInteractor(): IMenuProductInteractor = get()
    fun provideCafeInteractor(): ICafeInteractor = get()
    fun provideCartProductInteractor(): ICartProductInteractor = get()
    fun provideIUserInteractor(): IUserInteractor = get()

    //Use cases
    fun provideDisableUserUseCase(): DisableUserUseCase = get()
    fun provideGetUserAddressListUseCase(): GetUserAddressListUseCase = get()
    fun provideGetSelectableUserAddressListUseCase(): GetSelectableUserAddressListUseCase = get()
    fun provideGetCafeListUseCase(): GetCafeListUseCase = get()
    fun provideGetSelectableCafeListUseCase(): GetSelectableCafeListUseCase = get()
    fun provideGetCartTotalUseCase(): GetCartTotalUseCase = get()
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

    //Mapper
    fun provideUserAddressMapper(): UserAddressMapper = get()

    //Other
    fun provideApiRepo(): NetworkConnector = get()
    fun provideAnalyticService(): AnalyticService = get()
    fun provideCompanyUuidProvider(): CompanyUuidProvider = get()

}
