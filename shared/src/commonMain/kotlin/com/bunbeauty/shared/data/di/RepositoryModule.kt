package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.api.NetworkConnectorImpl
import com.bunbeauty.shared.data.network.socket.SocketService
import com.bunbeauty.shared.data.repository.CafeRepository
import com.bunbeauty.shared.data.repository.CartProductRepository
import com.bunbeauty.shared.data.repository.CityRepository
import com.bunbeauty.shared.data.repository.DeliveryRepository
import com.bunbeauty.shared.data.repository.MenuProductRepository
import com.bunbeauty.shared.data.repository.OrderRepository
import com.bunbeauty.shared.data.repository.PaymentRepository
import com.bunbeauty.shared.data.repository.SettingsRepository
import com.bunbeauty.shared.data.repository.StreetRepository
import com.bunbeauty.shared.data.repository.UserAddressRepository
import com.bunbeauty.shared.data.repository.UserRepository
import com.bunbeauty.shared.data.repository.VersionRepository
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.CityRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.repo.StreetRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import com.bunbeauty.shared.domain.repo.UserRepo
import com.bunbeauty.shared.domain.repo.VersionRepo
import org.koin.dsl.module

fun repositoryModule() = module {
    single {
        SocketService(
            uuidGenerator = get(),
            client = get(),
            json = get(),
        )
    }
    single<NetworkConnector> {
        NetworkConnectorImpl(
            client = get(),
            socketService = get(),
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
            orderMapper = get(),
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
    single {
        PaymentRepository(
            networkConnector = get(),
            dataStoreRepo = get(),
        )
    }
    single {
        SettingsRepository(
            dataStoreRepo = get(),
            networkConnector = get(),
            settingsMapper = get(),
        )
    }
}


