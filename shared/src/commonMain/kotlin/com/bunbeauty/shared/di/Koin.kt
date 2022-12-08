package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.mapperModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
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
import com.bunbeauty.shared.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.shared.domain.interactor.main.IMainInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.settings.ISettingsInteractor
import com.bunbeauty.shared.domain.interactor.street.IStreetInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.ui.create_order.CreateOrderViewModel
import com.bunbeauty.shared.ui.create_order.TimeMapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        databaseModule(),
        networkModule(),
        mapperModule(),
        repositoryModule(),
        interactorModule(),
        useCaseModule(),
        utilModule(),
        platformModule(),
        useCaseModule()
    )
}

fun initKoin() = startKoin {
    modules(
        databaseModule(),
        networkModule(),
        mapperModule(),
        repositoryModule(),
        interactorModule(),
        utilModule(),
        platformModule(),
        useCaseModule()
    )
}

class IosComponent:KoinComponent {
    fun provideMainInteractor(): IMainInteractor = get()
    fun provideCityInteractor(): ICityInteractor = get()
    fun provideApiRepo(): NetworkConnector = get()
    fun provideMenuInteractor(): IMenuProductInteractor = get()
    fun provideCafeInteractor(): ICafeInteractor = get()
    fun provideCartProductInteractor(): ICartProductInteractor = get()
    fun provideIUserInteractor(): IUserInteractor = get()
    fun provideISettingsInteractor(): ISettingsInteractor = get()
    fun provideIOrderInteractor(): IOrderInteractor = get()
    fun provideIAddressInteractor(): IAddressInteractor = get()
    fun provideIStreetInteractor(): IStreetInteractor = get()
    fun provideDisableUserUseCase(): DisableUserUseCase = get()
    fun provideIDeferredTimeInteractor(): IDeferredTimeInteractor = get()
    fun provideGetSelectedUserAddressUseCase(): GetSelectedUserAddressUseCase = get()
    fun provideGetSelectedCafeUseCase(): GetSelectedCafeUseCase = get()
    fun provideGetUserAddressListUseCase(): GetUserAddressListUseCase = get()
    fun provideGetCafeListUseCase(): GetCafeListUseCase = get()
    fun provideGetCartTotalUseCase(): GetCartTotalUseCase = get()
    fun provideGetMinTimeUseCase(): GetMinTimeUseCase = get()
    fun provideTimeMapper(): TimeMapper = get()
    fun provideUserAddressMapper(): UserAddressMapper = get()
    fun provideCreateOrderUseCase(): CreateOrderUseCase = get()

    fun provideCreateOrderViewModel(): CreateOrderViewModel = get()
}
