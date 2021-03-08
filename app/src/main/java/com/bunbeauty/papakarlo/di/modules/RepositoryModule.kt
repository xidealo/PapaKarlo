package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepository
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepository
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepository
import com.bunbeauty.papakarlo.data.local.db.delivery.DeliveryRepo
import com.bunbeauty.papakarlo.data.local.db.delivery.DeliveryRepository
import com.bunbeauty.papakarlo.data.local.db.district.DistrictRepo
import com.bunbeauty.papakarlo.data.local.db.district.DistrictRepository
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepository
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepository
import com.bunbeauty.papakarlo.data.local.db.street.StreetRepo
import com.bunbeauty.papakarlo.data.local.db.street.StreetRepository
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

    @Binds
    abstract fun bindDeliveryRepo(deliveryRepo: DeliveryRepository): DeliveryRepo
}