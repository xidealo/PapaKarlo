package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        MenuViewModel(
            menuProductInteractor = get(),
            stringUtil = get(),
            resourcesProvider = get(),
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
            stringUtil = get(),
            userInteractor = get(),
            cartProductInteractor = get(),
            resourcesProvider = get(),
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
    viewModel { parameters ->
        UserAddressListViewModel(
            addressInteractor = get(),
            stringUtil = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel {
        CafeAddressListViewModel(
            cafeInteractor = get(),
        )
    }
    viewModel { parameters ->
        CafeOptionsViewModel(
            resourcesProvider = get(),
            cafeInteractor = get(),
            savedStateHandle = parameters.get(),
        )
    }
    viewModel { EmptyViewModel() }
    viewModel { parameters ->
        OrderDetailsViewModel(
            orderInteractor = get(),
            orderUIMapper = get(),
            savedStateHandle = parameters.get(),
        )
    }
    viewModel {
        ProfileViewModel(
            userInteractor = get(),
            orderUIMapper = get(),
        )
    }
    viewModel { parameters ->
        LoginViewModel(
            textValidator = get(),
            userInteractor = get(),
            resourcesProvider = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel { parameters ->
        ConfirmViewModel(
            userInteractor = get(),
            resourcesProvider = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel {
        SettingsViewModel(
            userInteractor = get(),
            resourcesProvider = get(),
            settingsInteractor = get(),
        )
    }
    viewModel { parameters ->
        ProductDetailsViewModel(
            menuProductInteractor = get(),
            stringUtil = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel { parameters ->
        DeferredTimeViewModel(
            deferredTimeInteractor = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel {
        SelectCityViewModel(
            cityInteractor = get(),
            resourcesProvider = get(),
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