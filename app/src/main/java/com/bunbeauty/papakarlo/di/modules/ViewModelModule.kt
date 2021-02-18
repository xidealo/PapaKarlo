package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.di.annotation.ViewModelKey
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.presentation.SelectCityViewModel
import com.bunbeauty.papakarlo.presentation.SplashViewModel
import com.bunbeauty.papakarlo.presentation.address.CafeAddressesViewModel
import com.bunbeauty.papakarlo.presentation.address.CreationAddressViewModel
import com.bunbeauty.papakarlo.presentation.address.UserAddressesViewModel
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
import com.bunbeauty.papakarlo.presentation.cafe.CafeListViewModel
import com.bunbeauty.papakarlo.presentation.cafe.CafeOptionsViewModel
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.presentation.create_order.DeferredTimeViewModel
import com.bunbeauty.papakarlo.presentation.login.ConfirmViewModel
import com.bunbeauty.papakarlo.presentation.login.LoginViewModel
import com.bunbeauty.papakarlo.presentation.menu.MenuViewModel
import com.bunbeauty.papakarlo.presentation.menu.ProductTabViewModel
import com.bunbeauty.papakarlo.presentation.menu.ProductViewModel
import com.bunbeauty.papakarlo.presentation.profile.OrderDetailsViewModel
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import com.bunbeauty.papakarlo.presentation.profile.ProfileViewModel
import com.bunbeauty.papakarlo.presentation.profile.settings.CitySelectionViewModel
import com.bunbeauty.papakarlo.presentation.profile.settings.LogoutViewModel
import com.bunbeauty.papakarlo.presentation.profile.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductTabViewModel::class)
    internal abstract fun provideProductTabViewModel(productTabViewModel: ProductTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    internal abstract fun provideMenuViewModel(menuViewModel: MenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConsumerCartViewModel::class)
    internal abstract fun provideConsumerCartViewModel(consumerCartViewModel: ConsumerCartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateOrderViewModel::class)
    internal abstract fun provideCreationOrderViewModelImpl(creationOrderViewModel: CreateOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeListViewModel::class)
    internal abstract fun provideContactsViewModel(cafeListViewModel: CafeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel::class)
    internal abstract fun provideOrdersViewModel(ordersViewModel: OrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreationAddressViewModel::class)
    internal abstract fun provideCreationAddressViewModel(creationAddressViewModel: CreationAddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserAddressesViewModel::class)
    internal abstract fun provideUserAddressesViewModel(userAddressesViewModel: UserAddressesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeAddressesViewModel::class)
    internal abstract fun provideCafeAddressesViewModel(cafeAddressesViewModel: CafeAddressesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeOptionsViewModel::class)
    internal abstract fun provideCafeOptionsViewModel(cafeOptionsViewModel: CafeOptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmptyViewModel::class)
    internal abstract fun provideEmptyViewModel(emptyViewModel: EmptyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderDetailsViewModel::class)
    internal abstract fun provideOrderViewModel(orderDetailsViewModel: OrderDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun provideProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun provideLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmViewModel::class)
    internal abstract fun provideConfirmViewModel(confirmViewModel: ConfirmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun provideProductViewModel(productViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeferredTimeViewModel::class)
    internal abstract fun provideDeferredTimeViewModel(deferredTimeViewModel: DeferredTimeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCityViewModel::class)
    internal abstract fun provideSelectCityViewModel(selectCityViewModel: SelectCityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CitySelectionViewModel::class)
    internal abstract fun provideCitySelectionViewModel(citySelectionViewModel: CitySelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogoutViewModel::class)
    internal abstract fun provideLogoutViewModel(logoutViewModel: LogoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun provideSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}

fun viewModelModule() = module {
    viewModel {
        LoginViewModel(
            textValidator = get(),
            userInteractor = get(),
            resourcesProvider = get(),

            )
    }
    viewModel {
        ProductTabViewModel(
            menuProductRepo = get(),
            stringUtil = get(),
            productHelper = get(),
        )
    }
    viewModel { MenuViewModel() }
    viewModel {
        MainViewModel(
            cartProductInteractor = get(),
            mainInteractor = get(),
            stringUtil = get(),
            networkUtil = get()
        )
    }
    viewModel {
        ConsumerCartViewModel(
            cartProductRepo = get(),
            resourcesProvider = get(),
            dataStoreRepo = get(),
            stringUtil = get(),
            productHelper = get(),
            authUtil = get(),
        )
    }
    viewModel {
        CreateOrderViewModel(
            cartProductRepo = get(),
            userAddressRepo = get(),
            cafeRepo = get(),
            orderRepo = get(),
            dataStoreRepo = get(),
            stringUtil = get(),
            productHelper = get(),
            resourcesProvider = get(),
            orderUtil = get(),
            authUtil = get(),
            dateTimeUtils = get(),

            )
    }
    viewModel {
        CafeListViewModel(

            cafeInteractor = get(),
            resourcesProvider = get(),
            stringUtil = get(),
        )
    }
    viewModel {
        OrdersViewModel(

            orderRepo = get(),
            orderUIMapper = get(),
        )
    }
    viewModel {
        CreationAddressViewModel(
            userAddressRepo = get(),
            streetRepo = get(),
            dataStoreRepo = get(),
            resourcesProvider = get(),
            authUtil = get(),
            textValidator = get(),

            )
    }
    viewModel {
        UserAddressesViewModel(
            userAddressRepo = get(),
            stringUtil = get(),

            )
    }
    viewModel {
        CafeAddressesViewModel(
            cafeRepo = get(),
            stringUtil = get(),
        )
    }
    viewModel {
        CafeOptionsViewModel(
            resourcesProvider = get(),
            cafeInteractor = get(),
        )
    }
    viewModel { EmptyViewModel() }
    viewModel {
        OrderDetailsViewModel(
            orderRepo = get(),
            dataStoreRepo = get(),
            stringUtil = get(),
            productHelper = get(),
            orderUtil = get(),
            dateTimeUtil = get(),
        )
    }
    viewModel {
        ProfileViewModel(
            userRepo = get(),
            authUtil = get(),
            orderUIMapper = get(),
            userInteractor = get(),
        )
    }
    viewModel {
        LoginViewModel(
            textValidator = get(),
            userInteractor = get(),
            resourcesProvider = get(),
        )
    }
    viewModel {
        ConfirmViewModel(
            userInteractor = get(),
            resourcesProvider = get(),
        )
    }
    viewModel {
        SettingsViewModel(
            cityInteractor = get(),
            userInteractor = get(),
            resourcesProvider = get(),
        )
    }
    viewModel {
        ProductViewModel(
            menuProductRepo = get(),
            stringUtil = get(),
            productHelper = get(),
        )
    }
    viewModel {
        DeferredTimeViewModel(
            dateTimeUtils = get(),
        )
    }
    viewModel {
        SelectCityViewModel(
            cityInteractor = get()
        )
    }
    viewModel {
        CitySelectionViewModel(
            cityInteractor = get(),
        )
    }
    viewModel {
        LogoutViewModel(
            userInteractor = get(),
        )
    }
    viewModel {
        SplashViewModel(
            updateInteractor = get(),
            cityInteractor = get(),
        )
    }

}