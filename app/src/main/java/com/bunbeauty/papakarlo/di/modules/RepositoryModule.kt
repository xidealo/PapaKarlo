package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.data.repository.CafeRepository
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.data.repository.UserAddressRepository
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.data.repository.CartProductRepository
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.data.repository.DeliveryRepository
import com.bunbeauty.domain.repo.DistrictRepo
import com.bunbeauty.data.repository.DistrictRepository
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.data.repository.MenuProductRepository
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.data.repository.OrderRepository
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.data.repository.StreetRepository
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.data.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindCartProductRepo(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    fun bindOrderRepo(orderRepository: OrderRepository): OrderRepo

    @Binds
    fun bindMenuProductRepo(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    fun bindUserAddressRepo(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    fun bindCafeRepo(cafeRepository: CafeRepository): CafeRepo

    @Binds
    fun bindDistrictRepo(districtRepository: DistrictRepository): DistrictRepo

    @Binds
    fun bindStreetRepo(streetRepository: StreetRepository): StreetRepo

    @Binds
    fun bindDeliveryRepo(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    fun bindUserRepo(userRepository: UserRepository): UserRepo
}