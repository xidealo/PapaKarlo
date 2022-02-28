package com.bunbeauty.data.di

import com.bunbeauty.data.mapper.cafe.CafeMapper
import com.bunbeauty.data.mapper.cafe.ICafeMapper
import com.bunbeauty.data.mapper.cart_product.CartProductMapper
import com.bunbeauty.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.data.mapper.category.CategoryMapper
import com.bunbeauty.data.mapper.category.ICategoryMapper
import com.bunbeauty.data.mapper.city.CityMapper
import com.bunbeauty.data.mapper.city.ICityMapper
import com.bunbeauty.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.data.mapper.menuProduct.MenuProductMapper
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.order.OrderMapper
import com.bunbeauty.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.data.mapper.order_product.OrderProductMapper
import com.bunbeauty.data.mapper.profile.IProfileMapper
import com.bunbeauty.data.mapper.profile.ProfileMapper
import com.bunbeauty.data.mapper.street.IStreetMapper
import com.bunbeauty.data.mapper.street.StreetMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.mapper.user.UserMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.mapper.user_address.UserAddressMapper
import org.koin.dsl.module

fun mapperModule() = module {
    single<ICafeMapper> { CafeMapper() }
    single<IMenuProductMapper> { MenuProductMapper() }
    single<ICartProductMapper> { CartProductMapper() }
    single<IProfileMapper> {
        ProfileMapper(
            userAddressMapper = get(),
            orderMapper = get()
        )
    }
    single<IUserMapper> { UserMapper() }
    single<IUserAddressMapper> { UserAddressMapper(streetMapper = get()) }
    single<IStreetMapper> { StreetMapper() }
    single<ICityMapper> { CityMapper() }
    single<IOrderMapper> { OrderMapper(orderProductMapper = get(), dateTimeUtil = get()) }
    single<IOrderProductMapper> { OrderProductMapper() }
    single<ICategoryMapper> { CategoryMapper() }
}