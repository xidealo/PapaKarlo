package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data_firebase.mapper.*
import com.example.domain_firebase.mapper.*
import dagger.Binds
import dagger.Module

@Module
interface FirebaseMapperModule {

    @Binds
    fun provideCafeMapper(cafeMapper: CafeMapper): ICafeMapper

    @Binds
    fun provideCartProductMapper(cartProductMapper: CartProductMapper): ICartProductMapper

    @Binds
    fun provideMenuProductMapper(menuProductMapper: MenuProductMapper): IMenuProductMapper

    @Binds
    fun provideOrderMapper(orderMapper: OrderMapper): IOrderMapper

    @Binds
    fun provideOrderProductMapper(orderProductMapper: OrderProductMapper): IOrderProductMapper

    @Binds
    fun provideStreetMapper(streetMapper: StreetMapper): IStreetMapper

    @Binds
    fun provideUserAddressMapper(userAddressMapper: UserAddressMapper): IUserAddressMapper

    @Binds
    fun provideUserMapper(userMapper: UserMapper): IUserMapper
}