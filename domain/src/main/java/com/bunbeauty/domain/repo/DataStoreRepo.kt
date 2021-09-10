package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val userAddressUuid: Flow<String?>
    suspend fun saveUserAddressUuid(addressId: String)
    suspend fun clearUserAddressUuid()

    val cafeAddressUuid: Flow<String?>
    suspend fun saveCafeAddressUuid(addressId: String)

    val delivery: Flow<Delivery>
    suspend fun saveDelivery(delivery: Delivery)

    val userUuid: Flow<String?>
    suspend fun saveUserUuid(userId: String)

    val deferredTime: Flow<String?>
    suspend fun saveDeferredTime(deferredTime: String)

    val selectedCity: Flow<String?>
    suspend fun saveSelectedCity(city: String)
    suspend fun getSelectedCity(): String?

    suspend fun clearData()
}