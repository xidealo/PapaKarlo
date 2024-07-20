package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.ObserveLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.ObserveOrderListUseCase
import com.bunbeauty.shared.domain.feature.order.ObserveOrderUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.feature.orderavailable.GetIsOrderAvailableUseCase
import org.koin.dsl.module

internal fun orderUseCaseModule() = module {

    factory {
        CreateOrderUseCase(
            dataStoreRepo = get(),
            cartProductRepo = get(),
            dateTimeUtil = get(),
            orderRepo = get(),
        )
    }
    factory {
        ObserveLastOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
            lightOrderMapper = get(),
        )
    }
    factory {
        StopObserveOrdersUseCase(
            orderRepo = get()
        )
    }
    factory {
        ObserveOrderListUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
            lightOrderMapper = get(),
        )
    }
    factory {
        ObserveOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get(),
        )
    }
    factory {
        GetLastOrderUseCase(
            dataStoreRepo = get(),
            orderRepo = get()
        )
    }

    factory {
        GetIsOrderAvailableUseCase(
            orderAvailableRepository = get()
        )
    }
}