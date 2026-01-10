package com.bunbeauty.order.di

import com.bunbeauty.order.presentation.order_details.OrderDetailsViewModel
import com.bunbeauty.order.presentation.order_list.OrderListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun orderFeatureModule() = module {
    viewModel {
        OrderDetailsViewModel(
            observeOrderUseCase = get(),
            stopObserveOrdersUseCase = get(),
        )
    }

    viewModel {
        OrderListViewModel(
            observeOrderListUseCase = get(),
            stopObserveOrdersUseCase = get(),
        )
    }
}