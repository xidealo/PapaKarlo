package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.feature.auth.screen.confirm.ConfirmViewModel
import com.bunbeauty.papakarlo.feature.auth.screen.login.LoginViewModel
import com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list.CafeListViewModel
import com.bunbeauty.papakarlo.feature.cafe.screen.cafe_options.CafeOptionsViewModel
import com.bunbeauty.papakarlo.feature.city.screen.select_city.SelectCityViewModel
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.menu.MenuViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import com.bunbeauty.shared.presentation.product_details.ProductDetailsViewModel
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        MenuViewModel(
            menuProductInteractor = get(),
            stringUtil = get(),
            observeCartUseCase = get(),
            addCartProductUseCase = get(),
        )
    }
    viewModel {
        MainViewModel(
            networkUtil = get()
        )
    }
    viewModel {
        ConsumerCartViewModel(
            stringUtil = get(),
            userInteractor = get(),
            cartProductInteractor = get(),
            addCartProductUseCase = get(),
            removeCartProductUseCase = get(),
        )
    }
    viewModel {
        CreateOrderViewModel(
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
            saveSelectedUserAddressUseCase = get()
        )
    }
    viewModel {
        CafeListViewModel(
            cafeInteractor = get(),
            getSelectedCityTimeZoneUseCase = get(),
            getCafeListUseCase = get(),
            observeCartUseCase = get()
        )
    }
    viewModel {
        OrderListViewModel(
            observeOrderListUseCase = get(),
            stopObserveOrdersUseCase = get()
        )
    }
    viewModel {
        CreateAddressViewModel(
            getStreetsUseCase = get(),
            createAddressUseCase = get(),
            saveSelectedUserAddressUseCase = get(),
            getFilteredStreetListUseCase = get()
        )
    }
    viewModel {
        UserAddressListViewModel(
            getUserAddressList = get(),
            saveSelectedUserAddressUseCase = get(),
        )
    }
    viewModel { parameters ->
        CafeOptionsViewModel(
            cafeInteractor = get(),
            savedStateHandle = parameters.get(),
        )
    }
    viewModel { EmptyViewModel() }
    viewModel {
        OrderDetailsViewModel(
            observeOrderUseCase = get(),
            timeMapper = get(),
            stopObserveOrdersUseCase = get(),
        )
    }
    viewModel {
        ProfileViewModel(
            userInteractor = get(),
            observeLastOrderUseCase = get(),
            stopObserveOrdersUseCase = get(),
            getLastOrderUseCase = get(),
            observeCartUseCase = get(),
            getPaymentMethodListUseCase = get()
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
            successLoginDirection = parameters[0],
            phoneNumber = parameters[1],
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
    viewModel {
        ProductDetailsViewModel(
            getMenuProductByUuidUseCase = get(),
            observeCartUseCase = get(),
            addCartProductUseCase = get(),
        )
    }
    viewModel {
        SelectCityViewModel(
            cityInteractor = get()
        )
    }

    viewModel {
        SplashViewModel(
            updateInteractor = get(),
            cityInteractor = get(),
        )
    }
}
