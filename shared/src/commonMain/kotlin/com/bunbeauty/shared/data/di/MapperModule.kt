package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.mapper.SettingsMapper
import com.bunbeauty.shared.data.mapper.cart_product.CartProductMapper
import com.bunbeauty.shared.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.data.mapper.city.CityMapper
import com.bunbeauty.shared.data.mapper.city.ICityMapper
import com.bunbeauty.shared.data.mapper.link.LinkMapper
import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.mapper.menuProduct.MenuProductMapper
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.order.OrderMapper
import com.bunbeauty.shared.data.mapper.order_product.IOrderProductMapper
import com.bunbeauty.shared.data.mapper.order_product.OrderProductMapper
import com.bunbeauty.shared.data.mapper.payment.PaymentMethodMapper
import com.bunbeauty.shared.data.mapper.profile.IProfileMapper
import com.bunbeauty.shared.data.mapper.profile.ProfileMapper
import com.bunbeauty.shared.data.mapper.user.IUserMapper
import com.bunbeauty.shared.data.mapper.user.UserMapper
import org.koin.dsl.module

fun dataMapperModule() = module {
    factory<IMenuProductMapper> { MenuProductMapper() }
    factory<ICartProductMapper> { CartProductMapper() }
    factory<IProfileMapper> {
        ProfileMapper(
            userAddressMapper = get(),
            orderMapper = get()
        )
    }
    factory<IUserMapper> { UserMapper() }
    factory<ICityMapper> { CityMapper() }
    factory<IOrderMapper> { OrderMapper(orderProductMapper = get(), dateTimeUtil = get()) }
    factory<IOrderProductMapper> { OrderProductMapper() }
    factory {
        SettingsMapper()
    }
    factory {
        PaymentMethodMapper()
    }
    factory {
        LinkMapper()
    }
}
