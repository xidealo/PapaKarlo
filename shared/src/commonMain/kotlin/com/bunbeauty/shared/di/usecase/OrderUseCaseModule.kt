package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.domain.order.CreateOrderUseCase
import com.bunbeauty.core.domain.order.GetExtendedCommentUseCase
import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveOrderListUseCase
import com.bunbeauty.core.domain.order.ObserveOrderUseCase
import com.bunbeauty.core.domain.order.StopObserveOrdersUseCase
import com.bunbeauty.core.domain.orderavailable.IsOrderAvailableUseCase
import org.koin.dsl.module

internal fun orderUseCaseModule() =
    module {
        factory {
            CreateOrderUseCase(
                cartProductRepo = get(),
                dateTimeUtil = get(),
                orderRepo = get(),
            )
        }
        factory {
            ObserveLastOrderUseCase(
                orderRepo = get(),
                lightOrderMapper = get(),
            )
        }
        factory {
            StopObserveOrdersUseCase(
                orderRepo = get(),
            )
        }
        factory {
            ObserveOrderListUseCase(
                orderRepo = get(),
            )
        }
        factory {
            ObserveOrderUseCase(
                orderRepo = get(),
            )
        }
        factory {
            GetLastOrderUseCase(
                orderRepo = get(),
            )
        }
        factory {
            IsOrderAvailableUseCase(
                companyRepo = get(),
            )
        }
        factory {
            GetExtendedCommentUseCase()
        }
    }
