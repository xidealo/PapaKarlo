package com.bunbeauty.papakarlo.di.modules

import com.example.data_api.mapper.*
import com.example.domain_api.mapper.*
import dagger.Binds
import dagger.Module

@Module
interface ApiMapperModule {

    @Binds
    fun bindMenuProductMapper(menuProductMapper: MenuProductMapper): IMenuProductMapper

    @Binds
    fun bindCartProductMapper(cartProductMapper: CartProductMapper): ICartProductMapper

    @Binds
    fun bindCafeMapper(cafeMapper: CafeMapper): ICafeMapper
}