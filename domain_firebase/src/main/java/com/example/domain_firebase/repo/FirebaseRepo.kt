package com.example.domain_firebase.repo

import com.example.domain_firebase.model.firebase.MenuProductFirebase
import com.example.domain_firebase.model.firebase.UserFirebase
import com.example.domain_firebase.model.firebase.address.UserAddressFirebase
import com.example.domain_firebase.model.firebase.cafe.CafeFirebase
import com.example.domain_firebase.model.firebase.cafe.DeliveryFirebase
import com.example.domain_firebase.model.firebase.order.OrderFirebase
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import kotlinx.coroutines.flow.Flow

interface FirebaseRepo {
    fun postOrder(orderFirebase: OrderFirebase, cafeUuid: String): String
    fun postUser(userUuid: String, userFirebase: UserFirebase)
    fun postUserAddress(userAddress: UserAddressFirebase, userUuid: String): String

    fun updateUserEmail(userUuid: String, userEmail: String)

    fun getCafeList(): Flow<List<CafeFirebase>>
    fun getMenuProductMap(): Flow<Map<String, MenuProductFirebase>>
    fun getDelivery(): Flow<DeliveryFirebase?>
    fun getUser(userUuid: String): Flow<UserFirebase?>
    fun getOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?>

    fun observeOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?>

    fun removeOrderObservers()

    //fun getUserBonusList(userId: String): Flow<List<Int>>
}