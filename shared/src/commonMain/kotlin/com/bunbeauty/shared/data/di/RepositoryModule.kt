package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.network.AuthRepository
import com.bunbeauty.shared.data.network.api.ApiRepo
import com.bunbeauty.shared.data.network.api.ApiRepository
import com.bunbeauty.shared.data.repository.*
import com.bunbeauty.shared.domain.repo.*
import org.koin.dsl.module

fun repositoryModule() = module {
    single<ApiRepo> {
        ApiRepository(
            client = get(),
            json = get()
        )
    }
    single<CartProductRepo> {
        CartProductRepository(
            uuidGenerator = get(),
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
            apiRepo = get(),
            menuProductDao = get(),
            categoryDao = get(),
            menuProductCategoryReferenceDao = get(),
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
        )
    }
    single<StreetRepo> {
        StreetRepository(
            apiRepo = get(),
            streetDao = get(),
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
            userAddressDao = get(),
            orderDao = get(),
            dataStoreRepo = get()
        )
    }
    single<CityRepo> {
        CityRepository(
            apiRepo = get(),
            cityDao = get(),
            cityMapper = get(),
        )
    }
    single<AuthRepo> {
        AuthRepository(
            //firebaseAuth = get(),
        )
    }
    single<VersionRepo> {
        VersionRepository(
            apiRepo = get(),
        )
    }
}


