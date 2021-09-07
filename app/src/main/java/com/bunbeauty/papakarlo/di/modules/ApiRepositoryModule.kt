package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repo.*
import com.bunbeauty.papakarlo.di.Api
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
    fun bindCartProductRepo(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    @Api
    fun bindOrderRepo(orderRepository: OrderRepository): OrderRepo

    @Binds
    @Api
    fun bindMenuProductRepo(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    @Api
    fun bindUserAddressRepo(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    @Api
    fun bindCafeRepo(cafeRepository: CafeRepository): CafeRepo

    @Binds
    @Api
    fun bindStreetRepo(streetRepository: StreetRepository): StreetRepo

    @Binds
    @Api
    fun bindDeliveryRepo(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    @Api
    fun bindUserRepo(userRepository: UserRepository): UserRepo
}