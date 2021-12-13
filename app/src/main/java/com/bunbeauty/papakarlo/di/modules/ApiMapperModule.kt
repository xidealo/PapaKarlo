package com.bunbeauty.papakarlo.di.modules

import com.example.data_api.mapper.*
import com.example.domain_api.mapper.*
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface ApiMapperModule {

    @Binds
    fun bindMenuProductMapper(menuProductMapper: MenuProductMapper): IMenuProductMapper

    @Binds
    fun bindCartProductMapper(cartProductMapper: CartProductMapper): ICartProductMapper

    @Binds
    fun bindCafeMapper(cafeMapper: CafeMapper): ICafeMapper

    @Binds
    fun bindProfileMapper(userMapper: ProfileMapper): IProfileMapper

    @Binds
    fun bindUserAddressMapper(userAddressMapper: UserAddressMapper): IUserAddressMapper

    @Binds
    fun bindStreetMapper(streetMapper: StreetMapper): IStreetMapper

    @Binds
    fun bindCityMapper(cityMapper: CityMapper): ICityMapper

    @Binds
    fun bindOrderMapper(orderMapper: OrderMapper): IOrderMapper

    @Binds
    fun bindOrderProductMapper(orderProductMapper: OrderProductMapper): IOrderProductMapper
}

fun apiMapperModule() = module {
    single { CafeMapper() } bind ICafeMapper::class
    single { MenuProductMapper() } bind IMenuProductMapper::class
    single { CartProductMapper(menuProductMapper = get()) } bind ICartProductMapper::class
    single {
        ProfileMapper(
            userAddressMapper = get(),
            orderMapper = get(),
        )
    } bind IProfileMapper::class
    single { UserAddressMapper(streetMapper = get()) } bind IUserAddressMapper::class
    single { StreetMapper() } bind IStreetMapper::class
    single { CityMapper() } bind ICityMapper::class
    single { OrderMapper(orderProductMapper = get()) } bind IOrderMapper::class
    single { OrderProductMapper() } bind IOrderProductMapper::class
}

