package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data_firebase.Firebase
import com.bunbeauty.data_firebase.repository.*
import com.bunbeauty.domain.repo.*
import com.example.domain_firebase.repo.FirebaseRepo
import dagger.Binds
import dagger.Module

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