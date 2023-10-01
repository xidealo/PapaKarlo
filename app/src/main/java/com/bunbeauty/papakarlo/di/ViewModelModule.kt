package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.auth.screen.confirm.ConfirmViewModel
import com.bunbeauty.papakarlo.feature.auth.screen.login.LoginViewModel
import com.bunbeauty.papakarlo.feature.cafe.screen.cafeoptions.CafeOptionsViewModel
import com.bunbeauty.papakarlo.feature.city.screen.selectcity.SelectCityViewModel
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
import com.bunbeauty.shared.presentation.cafe_list.CafeListViewModel
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.shared.presentation.menu.MenuViewModel
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import com.bunbeauty.shared.presentation.product_details.ProductDetailsViewModel
import com.bunbeauty.shared.presentation.profile.ProfileViewModel
import com.bunbeauty.shared.presentation.settings.SettingsViewModel
import com.bunbeauty.shared.presentation.update.UpdateViewModel
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        MenuViewModel(
            menuProductInteractor = get(),
            observeCartUseCase = get(),
            addCartProductUseCase = get(),
            getDiscountUseCase = get()
        )
    }
    viewModel {
        MainViewModel(
            networkUtil = get()
        )
    }
    viewModel {
        ConsumerCartViewModel(
            userInteractor = get(),
            cartProductInteractor = get(),
            addCartProductUseCase = get(),
            removeCartProductUseCase = get()
        )
    }
    viewModel {
        CreateOrderViewModel(
            cartProductInteractor = get(),
            cafeInteractor = get(),
            userInteractor = get(),
            createOrderStateMapper = get(),
            timeMapper = get(),
            userAddressMapper = get(),
            getSelectableUserAddressList = get(),
            getSelectableCafeList = get(),
            getCartTotal = get(),
            getMinTime = get(),
            createOrder = get(),
            getSelectedCityTimeZone = get(),
            saveSelectedUserAddress = get(),
            getSelectablePaymentMethodListUseCase = get(),
            savePaymentMethodUseCase = get()
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
            getSelectableUserAddressListUseCase = get(),
            saveSelectedUserAddressUseCase = get()
        )
    }
    viewModel { parameters ->
        CafeOptionsViewModel(
            cafeInteractor = get(),
            resourcesProvider = get(),
            savedStateHandle = parameters.get()
        )
    }
    viewModel {
        OrderDetailsViewModel(
            observeOrderUseCase = get(),
            timeMapper = get(),
            stopObserveOrdersUseCase = get()
        )
    }
    viewModel {
        ProfileViewModel(
            userInteractor = get(),
            observeLastOrderUseCase = get(),
            stopObserveOrdersUseCase = get(),
            getLastOrderUseCase = get(),
            observeCartUseCase = get(),
            getPaymentMethodListUseCase = get(),
            getLinkListUseCase = get()
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
            phoneNumber = parameters[1]
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
            userInteractor = get()
        )
    }
    viewModel {
        ProductDetailsViewModel(
            getMenuProductByUuidUseCase = get(),
            observeCartUseCase = get(),
            addCartProductUseCase = get()
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
            cityInteractor = get()
        )
    }
    viewModel {
        UpdateViewModel(
            getLinkListUseCase = get()
        )
    }
}
