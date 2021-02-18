package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun clearToken()

    val userAddressUuid: Flow<String?>
    suspend fun saveUserAddressUuid(addressId: String)
    suspend fun clearUserAddressUuid()

    val cafeAddressUuid: Flow<String?>
    suspend fun saveCafeAddressUuid(addressId: String)

    val delivery: Flow<Delivery>
    suspend fun getDelivery(): Delivery
    suspend fun saveDelivery(delivery: Delivery)

    val userUuid: Flow<String?>
    suspend fun saveUserUuid(userId: String)
    suspend fun clearUserUuid()

    val deferredTime: Flow<String?>
    suspend fun saveDeferredTime(deferredTime: String)

    val selectedCityUuid: Flow<String?>
    suspend fun saveSelectedCityUuid(cityUuid: String)
    suspend fun getSelectedCityUuid(): String?

    suspend fun clearData()
}