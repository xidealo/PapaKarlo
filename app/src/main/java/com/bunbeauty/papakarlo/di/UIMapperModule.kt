package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import com.bunbeauty.papakarlo.mapper.order.OrderUIMapper
import org.koin.dsl.module

fun uiMapperModule() = module {
    single<IOrderUIMapper> {
        OrderUIMapper(
            stringUtil = get()
        )
    }
}