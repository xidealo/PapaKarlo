package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data.mapper.cart_product.CartProductMapper
import com.bunbeauty.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.data.mapper.menu_product.IMenuProductMapper
import com.bunbeauty.data.mapper.menu_product.MenuProductMapper
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.order.OrderMapper
import com.bunbeauty.data.mapper.user.IUserEntityMapper
import com.bunbeauty.data.mapper.user.IUserFirebaseMapper
import com.bunbeauty.data.mapper.user.UserEntityMapper
import com.bunbeauty.data.mapper.user.UserFirebaseMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.mapper.user_address.UserAddressMapper
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun provideUserFirebaseMapper(userFirebaseMapper: UserFirebaseMapper): IUserFirebaseMapper

    @Binds
    abstract fun provideUserEntityMapper(userEntityMapper: UserEntityMapper): IUserEntityMapper

    @Binds
    abstract fun provideCartProductMapper(cartProductMapper: CartProductMapper): ICartProductMapper

    @Binds
    abstract fun provideMenuProductMapper(menuProductMapper: MenuProductMapper): IMenuProductMapper

    @Binds
    abstract fun provideOrderMapper(orderMapper: OrderMapper): IOrderMapper

    @Binds
    abstract fun provideUserAddressMapper(UserAddressMapper: UserAddressMapper): IUserAddressMapper
}