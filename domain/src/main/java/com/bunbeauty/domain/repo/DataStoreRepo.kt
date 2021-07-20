package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.local.Delivery
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val userAddressUuid: Flow<String?>
    suspend fun saveUserAddressUuid(addressId: String)

    val cafeAddressUuid: Flow<String?>
    suspend fun saveCafeAddressUuid(addressId: String)

    val delivery: Flow<Delivery>
    suspend fun saveDelivery(delivery: Delivery)

    val userUuid: Flow<String?>
    suspend fun saveUserUuid(userId: String)

    val phone: Flow<String>
    suspend fun savePhone(phone: String)

    val email: Flow<String>
    suspend fun saveEmail(email: String)

    val deferredTime: Flow<String?>
    suspend fun saveDeferredTime(deferredTime: String)

    suspend fun clearData()
}