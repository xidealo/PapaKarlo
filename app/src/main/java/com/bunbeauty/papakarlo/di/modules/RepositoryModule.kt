package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repo.CafeAddressRepo
import com.bunbeauty.data.repository.CafeAddressRepository
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
abstract class RepositoryModule {
    @Binds
    abstract fun bindCartProductRepo(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    abstract fun bindOrderRepo(orderRepository: OrderRepository): OrderRepo

    @Binds
    abstract fun bindMenuProductRepo(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    abstract fun bindCafeAddressRepo(cafeAddressRepository: CafeAddressRepository): CafeAddressRepo

    @Binds
    abstract fun bindUserAddressRepo(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    abstract fun bindCafeRepo(cafeRepository: CafeRepository): CafeRepo

    @Binds
    abstract fun bindDistrictRepo(districtRepository: DistrictRepository): DistrictRepo

    @Binds
    abstract fun bindStreetRepo(streetRepository: StreetRepository): StreetRepo

    @Binds
    abstract fun bindDeliveryRepo(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    abstract fun bindUserRepo(userRepository: UserRepository): UserRepo
}