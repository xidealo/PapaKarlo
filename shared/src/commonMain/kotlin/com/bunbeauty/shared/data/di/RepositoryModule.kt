package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.repository.UserRepository
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.api.NetworkConnectorImpl
import com.bunbeauty.shared.data.repository.*
import com.bunbeauty.shared.domain.repo.*
import org.koin.dsl.module

fun repositoryModule() = module {
    single<NetworkConnector> {
        NetworkConnectorImpl()
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
            networkConnector = get(),
            orderMapper = get(),
        )
    }
    single<MenuProductRepo> {
        MenuProductRepository(
            networkConnector = get(),
            menuProductDao = get(),
            categoryDao = get(),
            menuProductCategoryReferenceDao = get(),
            menuProductMapper = get(),
        )
    }
    single<UserAddressRepo> {
        UserAddressRepository(
            networkConnector = get(),
            userAddressDao = get(),
            userAddressMapper = get(),
        )
    }
    single<CafeRepo> {
        CafeRepository(
            networkConnector = get(),
            dataStoreRepo = get(),
            cafeDao = get(),
            cafeMapper = get(),
        )
    }
    single<StreetRepo> {
        StreetRepository(
            networkConnector = get(),
            streetDao = get(),
            streetMapper = get(),
        )
    }
    single<DeliveryRepo> {
        DeliveryRepository(
            networkConnector = get(),
            dataStoreRepo = get(),
        )
    }
    single<UserRepo> {
        UserRepository(
            networkConnector = get(),
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
            networkConnector = get(),
            cityDao = get(),
            cityMapper = get(),
        )
    }
    single<VersionRepo> {
        VersionRepository(
            networkConnector = get(),
        )
    }
}


