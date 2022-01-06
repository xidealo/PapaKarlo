package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repo.*
import com.example.data_api.repository.*
import com.example.domain_api.repo.ApiRepo
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface ApiRepositoryModule {

    @Binds
    fun bindApiRepository(apiRepository: ApiRepository): ApiRepo

    @Binds
    @Api
    fun bindCartProductRepository(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    @Api
    fun bindOrderRepository(orderRepository: OrderRepository): OrderRepo

    @Binds
    @Api
    fun bindMenuProductRepository(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    @Api
    fun bindUserAddressRepository(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    @Api
    fun bindCafeRepository(cafeRepository: CafeRepository): CafeRepo

    @Binds
    @Api
    fun bindStreetRepository(streetRepository: StreetRepository): StreetRepo

    @Binds
    @Api
    fun bindDeliveryRepository(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    @Api
    fun bindUserRepository(userRepository: UserRepository): UserRepo

    @Binds
    @Api
    fun bindCityRepository(cityRepository: CityRepository): CityRepo

    @Binds
    @Api
    fun bindCategoryRepository(categoryRepository: CategoryRepository): CategoryRepo
}

fun apiRepositoryModule() = module {
    single {
        ApiRepository(
            client = get(),
            json = get()
        )
    } bind ApiRepo::class
    single {
        CartProductRepository(
            cartProductDao = get(),
            cartProductMapper = get(),
        )
    } bind CartProductRepo::class
    single {
        OrderRepository(
            orderDao = get(),
            apiRepo = get(),
            orderMapper = get(),
        )
    } bind OrderRepo::class
    single {
        MenuProductRepository(
            apiRepository = get(),
            menuProductDao = get(),
            menuProductMapper = get(),
        )
    } bind MenuProductRepo::class
    single {
        UserAddressRepository(
            apiRepo = get(),
            userAddressDao = get(),
            userAddressMapper = get(),
        )
    } bind UserAddressRepo::class
    single {
        CafeRepository(
            apiRepo = get(),
            dataStoreRepo = get(),
            cafeDao = get(),
            cafeMapper = get(),
            authRepo = get(),
        )
    } bind CafeRepo::class
    single {
        StreetRepository(
            apiRepo = get(),
            streetDao = get(),
            dataStoreRepo = get(),
            streetMapper = get(),
        )
    } bind StreetRepo::class
    single {
        DeliveryRepository(
            apiRepo = get(),
            dataStoreRepo = get(),
        )
    } bind DeliveryRepo::class
    single {
        UserRepository(
            apiRepo = get(),
            profileMapper = get(),
            userMapper = get(),
            userDao = get(),
            dataStoreRepo = get(),
        )
    } bind UserRepo::class
    single {
        CityRepository(
            apiRepo = get(),
            cityDao = get(),
            cityMapper = get(),
        )
    } bind CityRepo::class
    single<CategoryRepo> {
        CategoryRepository(
            apiRepository = get(),
            categoryMapper = get(),
            categoryDao = get()
        )
    }

}


