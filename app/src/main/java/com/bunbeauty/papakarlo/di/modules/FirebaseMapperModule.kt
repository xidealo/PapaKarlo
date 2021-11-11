package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data_firebase.mapper.*
import com.example.domain_firebase.mapper.*
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

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

fun firebaseMapperModule() = module {
    single { CafeMapper(streetMapper = get()) } bind ICafeMapper::class
    single { CartProductMapper(menuProductMapper = get()) } bind ICartProductMapper::class
    single { MenuProductMapper() } bind IMenuProductMapper::class
    single {
        OrderMapper(
            userAddressMapper = get(),
            orderProductMapper = get(),
            cafeMapper = get(),
        )
    } bind IOrderMapper::class
    single { OrderProductMapper(menuProductMapper = get()) } bind IOrderProductMapper::class
    single { StreetMapper() } bind IStreetMapper::class
    single { UserAddressMapper() } bind IUserAddressMapper::class
    single { UserMapper(userAddressMapper = get()) } bind IUserMapper::class
}