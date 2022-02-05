package com.bunbeauty.data.di

import com.bunbeauty.data.DataStoreRepository
import com.bunbeauty.data.network.AuthRepository
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.api.ApiRepository
import com.bunbeauty.data.repository.*
import com.bunbeauty.domain.repo.*
import dagger.Binds
import dagger.Module
import org.koin.dsl.module
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

fun apiRepositoryModule() = module {
    single<ApiRepo> {
        ApiRepository(
            client = get(),
            json = get()
        )
    }
    single<CartProductRepo> {
        CartProductRepository(
            cartProductDao = get(),
            cartProductMapper = get(),
        )
    }
    single<OrderRepo> {
        OrderRepository(
            orderDao = get(),
            apiRepo = get(),
            orderMapper = get(),
        )
    }
    single<MenuProductRepo> {
        MenuProductRepository(
            apiRepository = get(),
            menuProductDao = get(),
            menuProductMapper = get(),
        )
    }
    single<UserAddressRepo> {
        UserAddressRepository(
            apiRepo = get(),
            userAddressDao = get(),
            userAddressMapper = get(),
        )
    }
    single<CafeRepo> {
        CafeRepository(
            apiRepo = get(),
            dataStoreRepo = get(),
            cafeDao = get(),
            cafeMapper = get(),
            authRepo = get(),
        )
    }
    single<StreetRepo> {
        StreetRepository(
            apiRepo = get(),
            streetDao = get(),
            dataStoreRepo = get(),
            streetMapper = get(),
        )
    }
    single<DeliveryRepo> {
        DeliveryRepository(
            apiRepo = get(),
            dataStoreRepo = get(),
        )
    }
    single<UserRepo> {
        UserRepository(
            apiRepo = get(),
            profileMapper = get(),
            userMapper = get(),
            userDao = get(),
            dataStoreRepo = get(),
        )
    }
    single<CityRepo> {
        CityRepository(
            apiRepo = get(),
            cityDao = get(),
            cityMapper = get(),
        )
    }
    single<CategoryRepo> {
        CategoryRepository(
            apiRepository = get(),
            categoryMapper = get(),
            categoryDao = get()
        )
    }

}


