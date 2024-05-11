package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.cafe.screen.cafeoptions.CafeOptionsViewModel
import com.bunbeauty.papakarlo.feature.city.screen.selectcity.SelectCityViewModel
import com.bunbeauty.papakarlo.feature.main.MainViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashViewModel
import com.bunbeauty.shared.presentation.cafe_list.CafeListViewModel
import com.bunbeauty.shared.presentation.confirm.ConfirmViewModel
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import com.bunbeauty.shared.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.shared.presentation.login.LoginViewModel
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
            addMenuProductUseCase = get(),
            getDiscountUseCase = get(),
            analyticService = get()
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
            increaseCartProductCountUseCase = get(),
            addMenuProductUseCase = get(),
            removeCartProductUseCase = get(),
            getRecommendationsUseCase = get(),
            getMotivationUseCase = get(),
            analyticService = get()
        )
    }
    viewModel {
        CreateOrderViewModel(
            cartProductInteractor = get(),
            cafeInteractor = get(),
            userInteractor = get(),
            createOrderStateMapper = get(),
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
            observeCafeListUseCase = get(),
            getSelectedCityTimeZoneUseCase = get(),
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
            getSuggestionsUseCase = get(),
            createAddressUseCase = get(),
            saveSelectedUserAddressUseCase = get()
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
    viewModel {
        LoginViewModel(
            requestCode = get(),
            formatPhoneNumber = get(),
            getPhoneNumberCursorPosition = get(),
            checkPhoneNumber = get()
        )
    }
    viewModel {
        ConfirmViewModel(
            formatPhoneNumber = get(),
            checkCode = get(),
            resendCode = get(),
            analyticService = get()
        )
    }
    viewModel {
        SettingsViewModel(
            observeSettingsUseCase = get(),
            observeSelectedCityUseCase = get(),
            updateEmailUseCase = get(),
            getCityListUseCase = get(),
            saveSelectedCityUseCase = get(),
            disableUserUseCase = get(),
            userInteractor = get(),
            analyticService = get()
        )
    }
    viewModel {
        ProductDetailsViewModel(
            getMenuProductUseCase = get(),
            observeCartUseCase = get(),
            addCartProductUseCase = get(),
            analyticService = get(),
            editCartProductUseCase = get(),
            getAdditionGroupsWithSelectedAdditionUseCase = get(),
            getSelectedAdditionsPriceUseCase = get()
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
