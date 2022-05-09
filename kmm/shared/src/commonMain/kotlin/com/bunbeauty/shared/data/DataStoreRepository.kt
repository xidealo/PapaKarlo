package com.bunbeauty.shared.data

import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.UserCityUuid
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

expect class DataStoreRepository: DataStoreRepo, KoinComponent {
    override val token: Flow<String?>
    override suspend fun getToken(): String?
    override suspend fun saveToken(token: String)
    override suspend fun clearToken()
    override val userUuid: Flow<String?>
    override suspend fun getUserUuid(): String?
    override suspend fun saveUserUuid(userId: String)
    override suspend fun clearUserUuid()
    override val delivery: Flow<Delivery?>
    override suspend fun getDelivery(): Delivery?
    override suspend fun saveDelivery(delivery: Delivery)
    override val selectedCityUuid: Flow<String?>
    override val selectedCityTimeZone: Flow<String>
    override suspend fun saveSelectedCityUuid(cityUuid: String, cityTimeZone: String)
    override suspend fun getSelectedCityUuid(): String?
    override suspend fun getSelectedCityTimeZone(): String
    override fun observeUserAndCityUuid(): Flow<UserCityUuid>
    override suspend fun getUserAndCityUuid(): UserCityUuid
}