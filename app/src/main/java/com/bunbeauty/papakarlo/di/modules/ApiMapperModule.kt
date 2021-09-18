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

    @Binds
    fun bindProfileMapper(userMapper: UserMapper): IUserMapper

    @Binds
    fun bindUserAddressMapper(userAddressMapper: UserAddressMapper): IUserAddressMapper

    @Binds
    fun bindStreetMapper(streetMapper: StreetMapper): IStreetMapper

    @Binds
    fun bindCityMapper(cityMapper: CityMapper): ICityMapper

    @Binds
    fun bindOrderMapper(orderMapper: OrderMapper): IOrderMapper
}