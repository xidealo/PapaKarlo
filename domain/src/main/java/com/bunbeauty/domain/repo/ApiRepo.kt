package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.MenuProduct
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.firebase.AddressFirebase
import com.bunbeauty.domain.model.firebase.OrderFirebase
import com.bunbeauty.domain.model.firebase.UserFirebase
import kotlinx.coroutines.flow.Flow

interface ApiRepo {
    fun insert(orderFirebase: OrderFirebase, cafeId: String): String
    fun insert(userFirebase: UserFirebase, userId: String)
    fun update(userFirebase: UserFirebase, userId: String)
    fun insertToBonusList(userFirebase: UserFirebase, userId: String)
    fun insert(addressFirebase: AddressFirebase, userId: String): String
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList(): Flow<List<MenuProduct>>
    fun getDelivery(): Flow<Delivery>
    fun getUser(userId: String): Flow<UserFirebase?>
    fun getUserBonusList(userId: String): Flow<List<Int>>
    fun getOrder(cafeId: String, orderId: String): Flow<OrderFirebase?>
    fun getOrderWithSubscribe(cafeId: String, orderId: String): Flow<OrderFirebase?>
}