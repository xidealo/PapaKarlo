package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list.CafeListUiStateMapper
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.feature.order.screen.order_details.OrderProductItemMapper
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsUiStateMapper
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
}
