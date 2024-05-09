package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.address.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.feature.cafe.screen.cafelist.CafeListUiStateMapper
import com.bunbeauty.papakarlo.feature.createorder.mapper.CreateOrderStateMapper
import com.bunbeauty.papakarlo.feature.createorder.mapper.TimeMapper
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderDetailsUiStateMapper
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderProductItemMapper
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsUiStateMapper
import com.bunbeauty.papakarlo.feature.profile.screen.profile.LinkUiStateMapper
import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileUiStateMapper
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import org.koin.dsl.module

fun uiMapperModule() = module {
    factory {
        OrderItemMapper(
            stringUtil = get()
        )
    }
    factory {
        UserAddressItemMapper(
            stringUtil = get()
        )
    }
    factory {
        OrderProductItemMapper(
            stringUtil = get()
        )
    }
    factory {
        ProductDetailsUiStateMapper(
            stringUtil = get()
        )
    }
    factory {
        CafeListUiStateMapper(
            stringUtil = get()
        )
    }
    factory {
        ProfileUiStateMapper(
            stringUtil = get(),
            orderItemMapper = get()
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
        CreateOrderStateMapper(
            stringUtil = get(),
            paymentMethodUiStateMapper = get()
        )
    }
    factory {
        PaymentMethodUiStateMapper(
            resources = get()
        )
    }
    factory {
        LinkUiStateMapper()
    }
    factory {
        TimeMapper()
    }
}
