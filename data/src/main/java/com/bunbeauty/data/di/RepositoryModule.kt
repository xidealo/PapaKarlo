package com.bunbeauty.data.di

import com.bunbeauty.data.DataStoreRepository
import com.bunbeauty.data.network.AuthRepository
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.api.ApiRepository
import com.bunbeauty.data.repository.*
import com.bunbeauty.domain.repo.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun repositoryModule() = module {
    single<ApiRepo> {
        ApiRepository(
            client = get(),
            json = get()
        )
    }
    single<DataStoreRepo> {
        DataStoreRepository(
            context = androidContext()
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

    single<AuthRepo> {
        AuthRepository(
            firebaseAuth = get(),
        )
    }

    single<VersionRepo> {
        VersionRepository(
            apiRepo = get(),
        )
    }

}


