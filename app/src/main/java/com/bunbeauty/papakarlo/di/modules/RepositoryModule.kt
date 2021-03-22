package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.domain.repository.address.AddressRepository
import com.bunbeauty.domain.repository.cafe.CafeRepo
import com.bunbeauty.domain.repository.cafe.CafeRepository
import com.bunbeauty.domain.repository.cart_product.CartProductRepo
import com.bunbeauty.domain.repository.cart_product.CartProductRepository
import com.bunbeauty.domain.repository.district.DistrictRepo
import com.bunbeauty.domain.repository.district.DistrictRepository
import com.bunbeauty.domain.repository.menu_product.MenuProductRepo
import com.bunbeauty.domain.repository.menu_product.MenuProductRepository
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.domain.repository.order.OrderRepository
import com.bunbeauty.domain.repository.street.StreetRepo
import com.bunbeauty.domain.repository.street.StreetRepository
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
    abstract fun bindAddressRepo(addressRepository: AddressRepository): AddressRepo

    @Binds
    abstract fun bindCafeRepo(cafeRepository: CafeRepository): CafeRepo

    @Binds
    abstract fun bindDistrictRepo(districtRepository: DistrictRepository): DistrictRepo

    @Binds
    abstract fun bindStreetRepo(streetRepository: StreetRepository): StreetRepo
}