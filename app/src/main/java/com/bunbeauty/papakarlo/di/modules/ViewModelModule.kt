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