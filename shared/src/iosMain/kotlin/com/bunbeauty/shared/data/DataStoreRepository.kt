package com.bunbeauty.shared.data

import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.UserCityUuid
import com.bunbeauty.shared.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import platform.Foundation.NSUserDefaults

actual class DataStoreRepository() : DataStoreRepo, KoinComponent {

    actual override val token: Flow<String?> = flow {
        getToken()
    }

    actual override suspend fun getToken(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(TOKEN_KEY).toString()
    }

    actual override suspend fun saveToken(token: String) {
        NSUserDefaults.standardUserDefaults.setObject(token, TOKEN_KEY)
    }

    actual override suspend fun clearToken() {

    }

    actual override val delivery: Flow<Delivery?> = flow {

    }

    actual override suspend fun getDelivery(): Delivery? {
        return delivery.firstOrNull()
    }

    actual override suspend fun saveDelivery(delivery: Delivery) {

    }

    actual override val userUuid: Flow<String?> = flow {

    }

    actual override suspend fun getUserUuid(): String? {
        return userUuid.firstOrNull()
    }

    actual override suspend fun saveUserUuid(userId: String) {

    }

    actual override suspend fun clearUserUuid() {

    }

    actual override val selectedCityUuid: Flow<String?> = flow {

    }

    actual override val selectedCityTimeZone: Flow<String> = flow {

    }
    actual override suspend fun saveSelectedCityUuid(cityUuid: String, cityTimeZone: String) {
        NSUserDefaults.standardUserDefaults.setObject(cityUuid, SELECTED_CITY_UUID_KEY)
        NSUserDefaults.standardUserDefaults.setObject(cityTimeZone, SELECTED_CITY_TIME_ZONE_KEY)
    }

    actual override suspend fun getSelectedCityUuid(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(SELECTED_CITY_UUID_KEY).toString()
    }

    actual override suspend fun getSelectedCityTimeZone(): String {
        return NSUserDefaults.standardUserDefaults.stringForKey(SELECTED_CITY_TIME_ZONE_KEY).toString()
    }

    actual override fun observeUserAndCityUuid(): Flow<UserCityUuid> {
        return flow {

        }
    }

    actual override suspend fun getUserAndCityUuid(): UserCityUuid {
        return UserCityUuid(
            userUuid = getUserUuid() ?: "",
            cityUuid = getSelectedCityUuid() ?: ""
        )
    }

    companion object{
    const val TOKEN_KEY = "TOKEN_KEY"
    const val SELECTED_CITY_UUID_KEY = "SELECTED_CITY_UUID_KEY"
    const val SELECTED_CITY_TIME_ZONE_KEY = "SELECTED_CITY_TIME_ZONE_KEY"

    }


}