package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.api.NetworkConnectorImpl
import com.bunbeauty.shared.data.network.socket.SocketService
import com.bunbeauty.shared.data.repository.AdditionGroupRepository
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.AuthRepository
import com.bunbeauty.shared.data.repository.CafeRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.data.repository.CartProductRepository
import com.bunbeauty.shared.data.repository.CityRepository
import com.bunbeauty.shared.data.repository.CompanyRepository
import com.bunbeauty.shared.data.repository.DiscountRepository
import com.bunbeauty.shared.data.repository.LinkRepository
import com.bunbeauty.shared.data.repository.MenuProductRepository
import com.bunbeauty.shared.data.repository.OrderRepository
import com.bunbeauty.shared.data.repository.PaymentRepository
import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.data.repository.SettingsRepository
import com.bunbeauty.shared.data.repository.SuggestionRepository
import com.bunbeauty.shared.data.repository.UserAddressRepository
import com.bunbeauty.shared.data.repository.UserRepository
import com.bunbeauty.shared.data.repository.VersionRepository
import com.bunbeauty.core.domain.repo.AdditionGroupRepo
import com.bunbeauty.core.domain.repo.AdditionRepo
import com.bunbeauty.core.domain.repo.AuthRepo
import com.bunbeauty.core.domain.repo.CafeRepo
import com.bunbeauty.core.domain.repo.CartProductAdditionRepo
import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.domain.repo.CityRepo
import com.bunbeauty.core.domain.repo.CompanyRepo
import com.bunbeauty.core.domain.repo.DiscountRepo
import com.bunbeauty.core.domain.repo.LinkRepo
import com.bunbeauty.core.domain.repo.MenuProductRepo
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.domain.repo.PaymentRepo
import com.bunbeauty.core.domain.repo.RecommendationRepo
import com.bunbeauty.core.domain.repo.SettingsRepo
import com.bunbeauty.core.domain.repo.SuggestionRepo
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.domain.repo.UserRepo
import com.bunbeauty.core.domain.repo.VersionRepo
import org.koin.dsl.module

fun repositoryModule() =
    module {
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
                companyUuidProvider = get(),
            )
        }
        single<CartProductRepo> {
            CartProductRepository(
                uuidGenerator = get(),
                cartProductDao = get(),
                menuProductDao = get(),
                cartProductMapper = get(),
            )
        }
        single<OrderRepo> {
            OrderRepository(
                orderDao = get(),
                networkConnector = get(),
                orderMapper = get(),
                orderAdditionDao = get(),
                orderProductDao = get(),
                lightOrderDao = get(),
                dataStoreRepo = get()
            )
        }
        single<MenuProductRepo> {
            MenuProductRepository(
                networkConnector = get(),
                menuProductDao = get(),
                categoryDao = get(),
                menuProductCategoryReferenceDao = get(),
                menuProductMapper = get(),
                additionDao = get(),
                additionGroupDao = get(),
            )
        }
        single<UserAddressRepo> {
            UserAddressRepository(
                networkConnector = get(),
                userAddressDao = get(),
                userAddressMapper = get(),
                dataStoreRepo = get(),
            )
        }
        single<CafeRepo> {
            CafeRepository(
                networkConnector = get(),
                cafeStorage = get(),
                cafeDao = get(),
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
                dataStoreRepo = get(),
            )
        }
        single<CityRepo> {
            CityRepository(
                networkConnector = get(),
                cityDao = get(),
                cityMapper = get(),
                dataStoreRepo = get()
            )
        }
        single<CompanyRepo> {
            CompanyRepository(
                networkConnector = get(),
            )
        }
        single<VersionRepo> {
            VersionRepository(
                networkConnector = get(),
            )
        }
        single<SettingsRepo> {
            SettingsRepository(
                dataStoreRepo = get(),
                networkConnector = get(),
                settingsMapper = get(),
            )
        }
        single<PaymentRepo> {
            PaymentRepository(
                networkConnector = get(),
                paymentMethodMapper = get(),
                paymentMethodDao = get(),
            )
        }
        single<LinkRepo> {
            LinkRepository(
                networkConnector = get(),
                linkMapper = get(),
                linkDao = get(),
            )
        }
        single<DiscountRepo> {
            DiscountRepository(
                networkConnector = get(),
                dataStoreRepo = get(),
            )
        }
        single<AuthRepo> {
            AuthRepository(
                networkConnector = get(),
            )
        }
        single<RecommendationRepo> {
            RecommendationRepository(
                networkConnector = get(),
                dataStoreRepo = get(),
            )
        }
        single<CartProductAdditionRepo> {
            CartProductAdditionRepository(
                uuidGenerator = get(),
                cartProductAdditionDao = get(),
            )
        }
        single<AdditionRepo> {
            AdditionRepository(
                additionDao = get(),
            )
        }
        single<AdditionGroupRepo> {
            AdditionGroupRepository(
                additionGroupDao = get(),
            )
        }
        single<SuggestionRepo> {
            SuggestionRepository(
                networkConnector = get(),
            )
        }
    }
