package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.FirebaseAuthRepository
import com.bunbeauty.shared.data.di.dataMapperModule
import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.order.*
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.interactor.main.IMainInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.street.IStreetInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
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
        useCaseModule(),
        utilModule(),
        platformModule(),
        domainMapperModule(),
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
        useCaseModule(),
        domainMapperModule(),
    )
}

class IosComponent:KoinComponent {
    // Interactors
    fun provideMainInteractor(): IMainInteractor = get()
    fun provideCityInteractor(): ICityInteractor = get()
    fun provideMenuInteractor(): IMenuProductInteractor = get()
    fun provideCafeInteractor(): ICafeInteractor = get()
    fun provideCartProductInteractor(): ICartProductInteractor = get()
    fun provideIUserInteractor(): IUserInteractor = get()
    fun provideIOrderInteractor(): IOrderInteractor = get()
    fun provideIAddressInteractor(): IAddressInteractor = get()
    fun provideIStreetInteractor(): IStreetInteractor = get()

    //Use cases
    fun provideDisableUserUseCase(): DisableUserUseCase = get()
    fun provideGetSelectedUserAddressUseCase(): GetSelectedUserAddressUseCase = get()
    fun provideGetSelectedCafeUseCase(): GetSelectedCafeUseCase = get()
    fun provideGetUserAddressListUseCase(): GetUserAddressListUseCase = get()
    fun provideGetCafeListUseCase(): GetCafeListUseCase = get()
    fun provideGetCartTotalUseCase(): GetCartTotalUseCase = get()
    fun provideGetMinTimeUseCase(): GetMinTimeUseCase = get()
    fun provideCreateOrderUseCase(): CreateOrderUseCase = get()
    fun provideGetSelectedCityTimeZoneUseCase(): GetSelectedCityTimeZoneUseCase = get()
    fun provideStopObserveLastOrderUseCase(): StopObserveLastOrderUseCase = get()
    fun provideObserveSettingsUseCase(): ObserveSettingsUseCase = get()
    fun provideObserveSelectedCityUseCase(): ObserveSelectedCityUseCase = get()
    fun provideUpdateEmailUseCase(): UpdateEmailUseCase = get()
    fun provideGetCityListUseCase(): GetCityListUseCase = get()
    fun provideSaveSelectedCityUseCase(): SaveSelectedCityUseCase = get()
    fun provideObserveLastOrderUseCase(): ObserveLastOrderUseCase = get()
    fun provideStopObserveOrdersUseCase(): StopObserveOrdersUseCase = get()
    fun provideObserveOrderListUseCase(): ObserveOrderListUseCase = get()
    fun provideObserveOrderUseCase(): ObserveOrderUseCase = get()

    //Mapper
    fun provideTimeMapper(): TimeMapper = get()
    fun provideUserAddressMapper(): UserAddressMapper = get()

    //Other
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository = get()
    fun provideApiRepo(): NetworkConnector = get()

}
