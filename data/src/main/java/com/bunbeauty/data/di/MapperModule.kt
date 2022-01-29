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
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {

    @Binds
    fun bindMenuProductMapper(menuProductMapper: MenuProductMapper): IMenuProductMapper

    @Binds
    fun bindCartProductMapper(cartProductMapper: CartProductMapper): ICartProductMapper

    @Binds
    fun bindCafeMapper(cafeMapper: CafeMapper): ICafeMapper

    @Binds
    fun bindProfileMapper(profileMapper: ProfileMapper): IProfileMapper

    @Binds
    fun bindUserMapper(userMapper: UserMapper): IUserMapper

    @Binds
    fun bindUserAddressMapper(userAddressMapper: UserAddressMapper): IUserAddressMapper

    @Binds
    fun bindStreetMapper(streetMapper: StreetMapper): IStreetMapper

    @Binds
    fun bindCityMapper(cityMapper: CityMapper): ICityMapper

    @Binds
    fun bindOrderMapper(orderMapper: OrderMapper): IOrderMapper

    @Binds
    fun bindOrderProductMapper(orderProductMapper: OrderProductMapper): IOrderProductMapper

    @Binds
    fun bindCategoryMapper(categoryMapper: CategoryMapper): ICategoryMapper
}

//fun mapperModule() = module {
//    single { CafeMapper() } bind ICafeMapper::class
//    single<IMenuProductMapper> {
//        MenuProductMapper(
//            categoryMapper = get(),
//        )
//    }
//    single { CartProductMapper(menuProductMapper = get()) } bind ICartProductMapper::class
//    single {
//        ProfileMapper(
//            userAddressMapper = get(),
//            orderMapper = get(),
//            userMapper = get(),
//        )
//    } bind IProfileMapper::class
//    single<IUserMapper> { UserMapper() }
//    single { UserAddressMapper(streetMapper = get()) } bind IUserAddressMapper::class
//    single { StreetMapper() } bind IStreetMapper::class
//    single { CityMapper() } bind ICityMapper::class
//    single { OrderMapper(orderProductMapper = get()) } bind IOrderMapper::class
//    single { OrderProductMapper() } bind IOrderProductMapper::class
//    single<ICategoryMapper> { CategoryMapper() }
//}