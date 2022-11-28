package com.bunbeauty.papakarlo.di

import com.bunbeauty.shared.ui.create_order.TimeMapper
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressItemMapper
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import com.bunbeauty.papakarlo.mapper.order.OrderUIMapper
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import org.koin.dsl.module

fun uiMapperModule() = module {
    single<IOrderUIMapper> {
        OrderUIMapper(
            stringUtil = get()
        )
    }
    single {
        UserAddressItemMapper(
            stringUtil = get()
        )
    }
}
