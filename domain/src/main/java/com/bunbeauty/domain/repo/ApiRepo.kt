package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.firebase.cafe.CafeFirebase
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.entity.product.MenuProductEntity
import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import kotlinx.coroutines.flow.Flow

interface ApiRepo {
    fun postOrder(orderFirebase: OrderFirebase, cafeUuid: String): String
    fun postUser(userUuid: String, userFirebase: UserFirebase)
    fun postUserAddress(userAddress: UserAddressFirebase, userUuid: String): String

    fun updateUserEmail(userUuid: String, userEmail: String)

    fun getCafeList(): Flow<List<CafeFirebase>>
    fun getMenuProductList(): Flow<List<MenuProductFirebase>>
    fun getDelivery(): Flow<Delivery?>
    fun getUser(userUuid: String): Flow<UserFirebase?>
    fun getOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?>

    fun observeOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?>

    fun removeOrderObservers()

    //fun getUserBonusList(userId: String): Flow<List<Int>>
}