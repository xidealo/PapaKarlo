package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.mapper.SettingsMapper
import com.bunbeauty.shared.data.mapper.cafe.CafeMapper
import com.bunbeauty.shared.data.mapper.cafe.ICafeMapper
import com.bunbeauty.shared.data.mapper.cart_product.CartProductMapper
import com.bunbeauty.shared.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.data.mapper.city.CityMapper
import com.bunbeauty.shared.data.mapper.city.ICityMapper
import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.mapper.menuProduct.MenuProductMapper
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.order.OrderMapper
import com.bunbeauty.shared.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.shared.data.mapper.order_product.OrderProductMapper
import com.bunbeauty.shared.data.mapper.profile.IProfileMapper
import com.bunbeauty.shared.data.mapper.profile.ProfileMapper
import com.bunbeauty.shared.data.mapper.street.IStreetMapper
import com.bunbeauty.shared.data.mapper.street.StreetMapper
import com.bunbeauty.shared.data.mapper.user.IUserMapper
import com.bunbeauty.shared.data.mapper.user.UserMapper
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import org.koin.dsl.module

fun dataMapperModule() = module {
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
    single { UserAddressMapper(streetMapper = get()) }
    single<IStreetMapper> { StreetMapper() }
    single<ICityMapper> { CityMapper() }
    single<IOrderMapper> { OrderMapper(orderProductMapper = get(), dateTimeUtil = get()) }
    single<IOrderProductMapper> { OrderProductMapper() }
    factory {
        SettingsMapper()
    }
}