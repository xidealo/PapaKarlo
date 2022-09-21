package com.bunbeauty.shared

import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.Payment
import com.bunbeauty.shared.domain.model.UserCityUuid
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

    val delivery: Flow<Delivery?>
    suspend fun getDelivery(): Delivery?
    suspend fun saveDelivery(delivery: Delivery)

    val payment: Flow<Payment?>
    suspend fun getPayment(): Payment?
    suspend fun savePayment(payment: Payment)

    val selectedCityUuid: Flow<String?>
    val selectedCityTimeZone: Flow<String>
    suspend fun saveSelectedCityUuid(cityUuid: String, cityTimeZone: String)
    suspend fun getSelectedCityUuid(): String?
    suspend fun getSelectedCityTimeZone(): String

    fun observeUserAndCityUuid(): Flow<UserCityUuid>
    suspend fun getUserAndCityUuid(): UserCityUuid
}