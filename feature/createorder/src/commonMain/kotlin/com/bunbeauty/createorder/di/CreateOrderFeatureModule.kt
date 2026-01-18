package com.bunbeauty.createorder.di

import com.bunbeauty.createorder.presentation.createorder.CreateOrderViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun createOrderFeatureModule() =
    module {
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
    }
