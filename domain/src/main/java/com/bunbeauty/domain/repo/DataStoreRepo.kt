package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val deliveryAddressId: Flow<String>
    suspend fun saveDeliveryAddressId(addressId: String)

    val cafeAddressId: Flow<String>
    suspend fun saveCafeAddressId(addressId: String)

    val delivery: Flow<Delivery>
    suspend fun saveDelivery(delivery: Delivery)

    val userId: Flow<String>
    suspend fun saveUserId(userId: String)

    val phone: Flow<String>
    suspend fun savePhone(phone: String)

    val email: Flow<String>
    suspend fun saveEmail(email: String)

    suspend fun clearData()
}