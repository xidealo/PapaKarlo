package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderDetailsUiStateMapper
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderProductItemMapper
import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import org.koin.dsl.module

fun uiMapperModule() = module {
    factory {
        OrderItemMapper(
            stringUtil = get()
        )
    }
    factory {
        OrderProductItemMapper(
            stringUtil = get()
        )
    }
    factory {
        OrderDetailsUiStateMapper(
            stringUtil = get(),
            orderProductItemMapper = get(),
            paymentMethodUiStateMapper = get()
        )
    }
    factory {
        PaymentMethodUiStateMapper(
            resources = get()
        )
    }
}
