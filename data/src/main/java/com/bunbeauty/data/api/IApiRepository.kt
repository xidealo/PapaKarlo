package com.bunbeauty.data.api

import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.firebase.AddressFirebase
import com.bunbeauty.data.model.firebase.OrderFirebase
import com.bunbeauty.data.model.firebase.UserFirebase
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    fun insert(orderFirebase: OrderFirebase, cafeId: String): String
    fun insert(userFirebase: UserFirebase, userId: String)
    fun insert(addressFirebase: AddressFirebase, userId: String):String
    fun getCafeList(): Flow<List<Cafe>>
    fun getMenuProductList(): Flow<List<MenuProduct>>
    fun getDelivery(): Flow<Delivery>
}