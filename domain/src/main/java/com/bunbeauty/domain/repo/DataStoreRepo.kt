package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.UserCityUuid
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val token: Flow<String?>
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun clearToken()

    val userUuid: Flow<String?>
    suspend fun getUserUuid(): String?
    suspend fun saveUserUuid(userId: String)
    suspend fun clearUserUuid()

    val delivery: Flow<Delivery>
    suspend fun getDelivery(): Delivery
    suspend fun saveDelivery(delivery: Delivery)

    val deferredTime: Flow<String?>
    suspend fun saveDeferredTime(deferredTime: String)

    val selectedCityUuid: Flow<String?>
    suspend fun saveSelectedCityUuid(cityUuid: String)
    suspend fun getSelectedCityUuid(): String?

    fun observeUserAndCityUuid(): Flow<UserCityUuid>
    suspend fun getUserAndCityUuid(): UserCityUuid
}