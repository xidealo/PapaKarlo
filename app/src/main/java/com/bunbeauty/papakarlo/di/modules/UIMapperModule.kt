package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import com.bunbeauty.presentation.mapper.order.OrderUIMapper
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface UIMapperModule {

    @Binds
    fun bindOrderUIMapper(orderUIMapper: OrderUIMapper): IOrderUIMapper
}

fun uiMapperModule() = module {
    single {
        OrderUIMapper(
            dateTimeUtil = get(),
            stringUtil = get(),
            orderUtil = get(),
        )
    } bind IOrderUIMapper::class
}