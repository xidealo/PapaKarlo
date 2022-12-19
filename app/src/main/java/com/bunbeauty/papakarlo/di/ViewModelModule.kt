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
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuViewModel
import com.bunbeauty.papakarlo.feature.order.screen.order_details.OrderDetailsViewModel
import com.bunbeauty.papakarlo.feature.order.screen.order_list.OrderListViewModel
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutViewModel
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
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
            cafeInteractor = get(),
            userInteractor = get(),
            timeMapper = get(),
            userAddressMapper = get(),
            getSelectedUserAddress = get(),
            getSelectedCafe = get(),
            getUserAddressList = get(),
            getCafeList = get(),
            getCartTotal = get(),
            getMinTime = get(),
            createOrderUseCase = get(),
            getSelectedCityTimeZoneUseCase = get(),
        )
    }
    viewModel {
        CafeListViewModel(
            cafeInteractor = get(),
            getSelectedCityTimeZoneUseCase = get(),
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
            observeLastOrderUseCase = get(),
            stopObserveLastOrderUseCase = get()
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
            observeSettingsUseCase = get(),
            observeSelectedCityUseCase = get(),
            updateEmailUseCase = get(),
            getCityListUseCase = get(),
            saveSelectedCityUseCase = get(),
            firebaseAuthRepository = get(),
            disableUserUseCase = get(),
            userInteractor = get(),
        )
    }
    viewModel { parameters ->
        ProductDetailsViewModel(
            menuProductInteractor = get(),
            stringUtil = get(),
            savedStateHandle = parameters.get()
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
