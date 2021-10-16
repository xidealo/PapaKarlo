package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repo.*
import com.example.data_api.Api
import com.example.data_api.repository.*
import com.example.domain_api.repo.ApiRepo
import dagger.Binds
import dagger.Module

@Module
interface ApiRepositoryModule {

    @Binds
    fun bindApiRepository(apiRepository: ApiRepository): ApiRepo

    @Binds
    @Api
    fun bindCartProductRepository(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    @Api
    fun bindOrderRepository(orderRepository: OrderRepository): OrderRepo

    @Binds
    @Api
    fun bindMenuProductRepository(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    @Api
    fun bindUserAddressRepository(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    @Api
    fun bindCafeRepository(cafeRepository: CafeRepository): CafeRepo

    @Binds
    @Api
    fun bindStreetRepository(streetRepository: StreetRepository): StreetRepo

    @Binds
    @Api
    fun bindDeliveryRepository(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    @Api
    fun bindUserRepository(userRepository: UserRepository): UserRepo

    @Binds
    @Api
    fun bindCityRepository(cityRepository: CityRepository): CityRepo
}