package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.common.view_model.ViewModelFactory
import com.bunbeauty.papakarlo.di.annotation.ViewModelKey
import com.bunbeauty.papakarlo.feature.address.cafe_address_list.CafeAddressListViewModel
import com.bunbeauty.papakarlo.feature.address.create_address.CreateAddressViewModel
import com.bunbeauty.papakarlo.feature.address.user_address_list.UserAddressListViewModel
import com.bunbeauty.papakarlo.feature.auth.confirm.ConfirmViewModel
import com.bunbeauty.papakarlo.feature.auth.login.LoginViewModel
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeListViewModel
import com.bunbeauty.papakarlo.feature.cafe.cafe_options.CafeOptionsViewModel
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.feature.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.feature.create_order.deferred_time.DeferredTimeViewModel
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuViewModel
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsViewModel
import com.bunbeauty.papakarlo.feature.profile.ProfileViewModel
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderDetailsViewModel
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderListViewModel
import com.bunbeauty.papakarlo.feature.profile.settings.SettingsViewModel
import com.bunbeauty.papakarlo.feature.profile.settings.change_city.ChangeCityViewModel
import com.bunbeauty.papakarlo.feature.profile.settings.logout.LogoutViewModel
import com.bunbeauty.papakarlo.feature.select_city.SelectCityViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
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
    @ViewModelKey(OrderListViewModel::class)
    internal abstract fun provideOrdersViewModel(orderListViewModel: OrderListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateAddressViewModel::class)
    internal abstract fun provideCreationAddressViewModel(createAddressViewModel: CreateAddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserAddressListViewModel::class)
    internal abstract fun provideUserAddressesViewModel(userAddressListViewModel: UserAddressListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeAddressListViewModel::class)
    internal abstract fun provideCafeAddressesViewModel(cafeAddressListViewModel: CafeAddressListViewModel): ViewModel

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
    @ViewModelKey(ProductDetailsViewModel::class)
    internal abstract fun provideProductViewModel(productDetailsViewModel: ProductDetailsViewModel): ViewModel

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
    @ViewModelKey(ChangeCityViewModel::class)
    internal abstract fun provideCitySelectionViewModel(changeCityViewModel: ChangeCityViewModel): ViewModel

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
        MenuViewModel(
            categoryInteractor = get(),
            menuProductInteractor = get(),
            stringUtil = get()
        )
    }
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
            resourcesProvider = get(),
            stringUtil = get(),
            userInteractor = get(),
            cartProductInteractor = get(),
        )
    }
    viewModel {
        CreateOrderViewModel(
            addressInteractor = get(),
            cartProductInteractor = get(),
            orderInteractor = get(),
            cafeInteractor = get(),
            userInteractor = get(),
            deferredTimeInteractor = get(),
            stringUtil = get(),
            resourcesProvider = get(),
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
        OrderListViewModel(
            orderUIMapper = get(),
            orderInteractor = get(),
            userInteractor = get(),
        )
    }
    viewModel {
        CreateAddressViewModel(
            resourcesProvider = get(),
            textValidator = get(),
            streetInteractor = get(),
            addressInteractor = get()
        )
    }
    viewModel {
        UserAddressListViewModel(
            addressInteractor = get(),
            stringUtil = get()
        )
    }
    viewModel {
        CafeAddressListViewModel(
            cafeInteractor = get(),
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
            orderInteractor = get(),
            productInteractor = get(),
            stringUtil = get(),
            orderUIMapper = get(),
        )
    }
    viewModel {
        ProfileViewModel(
            userInteractor = get(),
            orderUIMapper = get(),
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
            userInteractor = get(),
            resourcesProvider = get(),
            settingsInteractor = get(),
        )
    }
    viewModel {
        ProductDetailsViewModel(
            menuProductInteractor = get(),
            stringUtil = get()
        )
    }
    viewModel {
        DeferredTimeViewModel(
            resourcesProvider = get(),
            deferredTimeInteractor = get(),
        )
    }
    viewModel {
        SelectCityViewModel(
            cityInteractor = get()
        )
    }
    viewModel {
        ChangeCityViewModel(
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