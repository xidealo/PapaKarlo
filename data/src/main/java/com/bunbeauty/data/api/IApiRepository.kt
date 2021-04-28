package com.bunbeauty.data.api

import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.firebase.OrderFirebase
import com.bunbeauty.data.model.firebase.UserFirebase
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    suspend fun insertOrder(orderFirebase: OrderFirebase, cafeId: String): String
    suspend fun insertUser(userFirebase: UserFirebase, userId: String)
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList(): Flow<List<MenuProduct>>
    fun getDelivery(): Flow<Delivery>
}