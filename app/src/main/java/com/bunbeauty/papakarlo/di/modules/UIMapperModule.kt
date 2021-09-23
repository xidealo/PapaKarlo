package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import com.bunbeauty.presentation.mapper.order.OrderUIMapper
import dagger.Binds
import dagger.Module

@Module
interface UIMapperModule {

    @Binds
    fun bindOrderUIMapper(orderUIMapper: OrderUIMapper): IOrderUIMapper
}