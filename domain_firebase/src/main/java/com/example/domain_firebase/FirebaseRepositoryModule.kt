package com.example.domain_firebase

import com.bunbeauty.data_firebase.Firebase
import com.bunbeauty.data_firebase.repository.*
import com.bunbeauty.domain.repo.*
import com.example.domain_firebase.repo.FirebaseRepo
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface FirebaseRepositoryModule {

    @Binds
    fun bindFirebaseRepository(firebaseRepository: FirebaseRepository): FirebaseRepo

    @Binds
    @Firebase
    fun bindCartProductRepo(cartProductRepository: CartProductRepository): CartProductRepo

    @Binds
    @Firebase
    fun bindOrderRepo(orderRepository: OrderRepository): OrderRepo

    @Binds
    @Firebase
    fun bindMenuProductRepo(menuProductRepository: MenuProductRepository): MenuProductRepo

    @Binds
    @Firebase
    fun bindUserAddressRepo(userAddressRepository: UserAddressRepository): UserAddressRepo

    @Binds
    @Firebase
    fun bindCafeRepo(cafeRepository: CafeRepository): CafeRepo

    @Binds
    @Firebase
    fun bindStreetRepo(streetRepository: StreetRepository): StreetRepo

    @Binds
    @Firebase
    fun bindDeliveryRepo(deliveryRepository: DeliveryRepository): DeliveryRepo

    @Binds
    @Firebase
    fun bindUserRepo(userRepository: UserRepository): UserRepo
}

fun firebaseRepositoryModule() = module {
    single { FirebaseRepository(firebaseDatabase = get()) } bind FirebaseRepo::class
    single {
        CartProductRepository(
            cartProductDao = get(),
            cartProductMapper = get(),
        )
    } bind CartProductRepo::class
    single {
        OrderRepository(
            orderDao = get(),
            cafeDao = get(),
            orderMapper = get(),
        )
    } bind OrderRepo::class
    single {
        MenuProductRepository(
            menuProductDao = get(),
            menuProductMapper = get(),
            firebaseRepo = get(),
        )
    } bind MenuProductRepo::class
    single {
        UserAddressRepository(
            userAddressDao = get(),
            userAddressMapper = get(),
        )
    } bind UserAddressRepo::class
    single {
        CafeRepository(
            cafeDao = get(),
            firebaseRepo = get(),
            cafeMapper = get(),
        )
    } bind CafeRepo::class
    single {
        StreetRepository(
            streetDao = get(),
            streetMapper = get(),
        )
    } bind StreetRepo::class
    single {
        DeliveryRepository(
            firebaseRepo = get(),
            dataStoreRepo = get(),
        )
    } bind DeliveryRepo::class
    single {
        UserRepository(
            userDao = get(),
            orderDao = get(),
            userAddressDao = get(),
            firebaseRepo = get(),
            userMapper = get(),
            orderMapper = get(),
        )
    } bind UserRepo::class
}

