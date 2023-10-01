package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.FirebaseAuthRepository
import com.bunbeauty.shared.data.di.dataMapperModule
import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.di.usecase.authUseCaseModule
import com.bunbeauty.shared.di.usecase.cafeUseCaseModule
import com.bunbeauty.shared.di.usecase.cartUseCaseModule
import com.bunbeauty.shared.di.usecase.cityUseCaseModule
import com.bunbeauty.shared.di.usecase.orderUseCaseModule
import com.bunbeauty.shared.di.usecase.paymentUseCaseModule
import com.bunbeauty.shared.di.usecase.useCaseModules
import com.bunbeauty.shared.di.usecase.userAddressUseCaseModule
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
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
import com.bunbeauty.shared.domain.use_case.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.feature.address.GetStreetsUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.domain.use_case.address.*
import com.bunbeauty.shared.domain.use_case.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.presentation.create_order.CreateOrderStateMapper
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        databaseModule(),
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
        cartUseCaseModule(),
        cafeUseCaseModule(),
        paymentUseCaseModule(),
        authUseCaseModule(),
        useCaseModules()
    )
}

fun initKoin() = startKoin {
    modules(
        databaseModule(),
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
        cartUseCaseModule(),
        cafeUseCaseModule(),
        paymentUseCaseModule(),
        authUseCaseModule(),
        useCaseModules()
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
    fun provideGetStreetsUseCase(): GetStreetsUseCase = get()
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

    fun provideSavePaymentMethodUseCase(): SavePaymentMethodUseCase = get()

    //Mapper
    fun provideTimeMapper(): TimeMapper = get()
    fun provideUserAddressMapper(): UserAddressMapper = get()
    fun provideCreateOrderStateMapper(): CreateOrderStateMapper = get()

    //Other
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository = get()
    fun provideApiRepo(): NetworkConnector = get()

}
