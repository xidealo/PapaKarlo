package com.bunbeauty.data.di

import com.bunbeauty.data.DataStoreRepository
import com.bunbeauty.data.network.AuthRepository
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.api.ApiRepository
import com.bunbeauty.data.repository.*
import com.bunbeauty.domain.repo.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindDataStoreRepository(dataStoreRepository: DataStoreRepository): DataStoreRepo

    @Binds
    fun bindApiRepository(apiRepository: ApiRepository): ApiRepo

    @Binds
    fun bindAuthRepository(authRepository: AuthRepository): AuthRepo

    @Binds
    fun bindCartProductRepository(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    fun bindOrderRepository(orderRepository: OrderRepository): OrderRepo

    @Binds
    fun bindMenuProductRepository(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    fun bindUserAddressRepository(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    fun bindCafeRepository(cafeRepository: CafeRepository): CafeRepo

    @Binds
    fun bindStreetRepository(streetRepository: StreetRepository): StreetRepo

    @Binds
    fun bindDeliveryRepository(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    fun bindUserRepository(userRepository: UserRepository): UserRepo

    @Binds
    fun bindCityRepository(cityRepository: CityRepository): CityRepo

    @Binds
    fun bindCategoryRepository(categoryRepository: CategoryRepository): CategoryRepo

    @Singleton
    @Binds
    fun bindVersionRepository(versionRepository: VersionRepository): VersionRepo
}

//fun apiRepositoryModule() = module {
//    single {
//        ApiRepository(
//            client = get(),
//            json = get()
//        )
//    } bind ApiRepo::class
//    single {
//        CartProductRepository(
//            cartProductDao = get(),
//            cartProductMapper = get(),
//        )
//    } bind CartProductRepo::class
//    single {
//        OrderRepository(
//            orderDao = get(),
//            apiRepo = get(),
//            orderMapper = get(),
//        )
//    } bind OrderRepo::class
//    single {
//        MenuProductRepository(
//            apiRepository = get(),
//            menuProductDao = get(),
//            menuProductMapper = get(),
//        )
//    } bind MenuProductRepo::class
//    single {
//        UserAddressRepository(
//            apiRepo = get(),
//            userAddressDao = get(),
//            userAddressMapper = get(),
//        )
//    } bind UserAddressRepo::class
//    single {
//        CafeRepository(
//            apiRepo = get(),
//            dataStoreRepo = get(),
//            cafeDao = get(),
//            cafeMapper = get(),
//            authRepo = get(),
//        )
//    } bind CafeRepo::class
//    single {
//        StreetRepository(
//            apiRepo = get(),
//            streetDao = get(),
//            dataStoreRepo = get(),
//            streetMapper = get(),
//        )
//    } bind StreetRepo::class
//    single {
//        DeliveryRepository(
//            apiRepo = get(),
//            dataStoreRepo = get(),
//        )
//    } bind DeliveryRepo::class
//    single {
//        UserRepository(
//            apiRepo = get(),
//            profileMapper = get(),
//            userMapper = get(),
//            userDao = get(),
//            dataStoreRepo = get(),
//        )
//    } bind UserRepo::class
//    single {
//        CityRepository(
//            apiRepo = get(),
//            cityDao = get(),
//            cityMapper = get(),
//        )
//    } bind CityRepo::class
//    single<CategoryRepo> {
//        CategoryRepository(
//            apiRepository = get(),
//            categoryMapper = get(),
//            categoryDao = get()
//        )
//    }
//
//}


