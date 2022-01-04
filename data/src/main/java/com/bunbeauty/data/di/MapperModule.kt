package com.bunbeauty.data.di

import com.bunbeauty.data.mapper.OrderMapper
import com.bunbeauty.domain.mapper.IOrderMapper
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {

    @Binds
    fun bindsOrderMapper(orderMapper: OrderMapper): IOrderMapper
}