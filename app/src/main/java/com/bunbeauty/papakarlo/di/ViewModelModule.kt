package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.feature.address.screen.cafe_address_list.CafeAddressListViewModel
import com.bunbeauty.papakarlo.feature.address.screen.create_address.CreateAddressViewModel
import com.bunbeauty.papakarlo.feature.address.screen.user_address_list.UserAddressListViewModel
import com.bunbeauty.papakarlo.feature.auth.screen.confirm.ConfirmViewModel
import com.bunbeauty.papakarlo.feature.auth.screen.login.LoginViewModel
import com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list.CafeListViewModel
import com.bunbeauty.papakarlo.feature.cafe.screen.cafe_options.CafeOptionsViewModel
import com.bunbeauty.papakarlo.feature.city.screen.change_city.ChangeCityViewModel
import com.bunbeauty.papakarlo.feature.city.screen.select_city.SelectCityViewModel
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.feature.create_order.screen.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time.DeferredTimeViewModel
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuViewModel
import com.bunbeauty.papakarlo.feature.order.screen.order_details.OrderDetailsViewModel
import com.bunbeauty.papakarlo.feature.order.screen.order_list.OrderListViewModel
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        MenuViewModel(
            menuProductInteractor = get(),
            stringUtil = get(),
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
            timeMapper = get(),
        )
    }
    viewModel {
        CafeListViewModel(
            cafeInteractor = get(),
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
            orderInteractor = get(),
            orderUIMapper = get(),
        )
    }
    viewModel { parameters ->
        LoginViewModel(
            textValidator = get(),
            userInteractor = get(),
            firebaseAuthRepository = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel { parameters ->
        ConfirmViewModel(
            userInteractor = get(),
            firebaseAuthRepository = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel {
        SettingsViewModel(
            userInteractor = get(),
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
            timeMapper = get(),
            savedStateHandle = parameters.get(),
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
            firebaseAuthRepository = get(),
            userInteractor = get(),
        )
    }
    viewModel {
        SplashViewModel(
            updateInteractor = get(),
            cityInteractor = get(),
        )
    }
    viewModel {
        PaymentViewModel(
            paymentInteractor = get()
        )
    }
}