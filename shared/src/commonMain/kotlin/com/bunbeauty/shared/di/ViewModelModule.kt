package com.bunbeauty.shared.di

import com.bunbeauty.shared.presentation.MainViewModel
import com.bunbeauty.shared.presentation.cafe_list.CafeListViewModel
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.shared.presentation.create_address.CreateAddressViewModel
import com.bunbeauty.shared.presentation.createorder.CreateOrderViewModel
import com.bunbeauty.shared.presentation.order_details.OrderDetailsViewModel
import com.bunbeauty.shared.presentation.order_list.OrderListViewModel
import com.bunbeauty.profile.presentation.selectcity.SelectCityViewModel
import com.bunbeauty.shared.presentation.splash.SplashViewModel
import com.bunbeauty.shared.presentation.update.UpdateViewModel
import com.bunbeauty.shared.presentation.user_address_list.UserAddressListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() =
    module {
        viewModel {
            MainViewModel(
                isOrderAvailableUseCase = get(),
                networkUtil = get(),
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
                analyticService = get(),
                isOrderAvailableUseCase = get(),
            )
        }
        viewModel {
            CreateOrderViewModel(
                cartProductInteractor = get(),
                cafeInteractor = get(),
                userInteractor = get(),
                getSelectableUserAddressList = get(),
                getSelectableCafeList = get(),
                getCartTotalFlowUseCase = get(),
                getMotivationUseCase = get(),
                getMinTime = get(),
                createOrder = get(),
                getSelectedCityTimeZone = get(),
                saveSelectedUserAddress = get(),
                getSelectablePaymentMethodListUseCase = get(),
                savePaymentMethodUseCase = get(),
                getCurrentUserAddressWithCityUseCase = get(),
                isDeliveryEnabledFromCafeUseCase = get(),
                isPickupEnabledFromCafeUseCase = get(),
                hasOpenedCafeUseCase = get(),
                getWorkloadCafeUseCase = get(),
                getSelectedPaymentMethodUseCase = get(),
                getExtendedCommentUseCase = get(),
                getAdditionalUtensilsUseCase = get(),
                getDeferredTimeHintUseCase = get(),
            )
        }
        viewModel {
            CafeListViewModel(
                cafeInteractor = get(),
                observeCafeWithOpenStateListUseCase = get(),
            )
        }
        viewModel {
            OrderListViewModel(
                observeOrderListUseCase = get(),
                stopObserveOrdersUseCase = get(),
            )
        }
        viewModel {
            CreateAddressViewModel(
                getSuggestionsUseCase = get(),
                createAddressUseCase = get(),
                saveSelectedUserAddressUseCase = get(),
            )
        }
        viewModel {
            UserAddressListViewModel(
                getSelectableUserAddressListUseCase = get(),
                saveSelectedUserAddressUseCase = get(),
            )
        }
        viewModel {
            OrderDetailsViewModel(
                observeOrderUseCase = get(),
                stopObserveOrdersUseCase = get(),
            )
        }


        viewModel {
            SelectCityViewModel(
                cityInteractor = get(),
            )
        }
        viewModel {
            SplashViewModel(
                checkUpdateUseCase = get(),
                cityInteractor = get(),
                getIsOneCityUseCase = get(),
                saveOneCityUseCase = get(),
            )
        }
        viewModel {
            UpdateViewModel(
                getLinkUseCase = get(),
            )
        }
    }
