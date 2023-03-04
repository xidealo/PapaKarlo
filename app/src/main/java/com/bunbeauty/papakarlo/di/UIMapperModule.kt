package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.feature.order.screen.order_details.OrderProductItemMapper
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsUiStateMapper
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import org.koin.dsl.module

fun uiMapperModule() = module {
    single {
        OrderItemMapper(
            stringUtil = get()
        )
    }
    single {
        UserAddressItemMapper(
            stringUtil = get()
        )
    }
    single {
        OrderProductItemMapper(
            stringUtil = get()
        )
    }
    single {
        ProductDetailsUiStateMapper(
            stringUtil = get()
        )
    }
}
