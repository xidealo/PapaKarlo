package com.bunbeauty.shared.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.UserCityUuid
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class DataStoreRepository : DataStoreRepo, KoinComponent {

    private val context: Context by inject()

    private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATA_STORE)
    private val Context.deliveryDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_DATA_STORE)
    private val Context.userUuidDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_UUID_DATA_STORE)
    private val Context.selectedCityDataStore: DataStore<Preferences> by preferencesDataStore(name = SELECTED_CITY_DATA_STORE)

    actual override val token: Flow<String?> = context.tokenDataStore.data.map {
        it[TOKEN_KEY]
    }

    actual override suspend fun getToken(): String? {
        return token.first()
    }

    actual override suspend fun saveToken(token: String) {
        context.tokenDataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    actual override suspend fun clearToken() {
        context.tokenDataStore.edit {
            it.clear()
        }
    }

    actual override val delivery: Flow<Delivery?> = context.deliveryDataStore.data.map {
        it[DELIVERY_COST_KEY]?.let { cost ->
            it[FOR_FREE_DELIVERY_KEY]?.let { forFree ->
                Delivery(cost = cost, forFree = forFree)
            }
        }
    }

    actual override suspend fun getDelivery(): Delivery? {
        return delivery.firstOrNull()
    }

    actual override suspend fun saveDelivery(delivery: Delivery) {
        context.deliveryDataStore.edit {
            it[DELIVERY_COST_KEY] = delivery.cost
            it[FOR_FREE_DELIVERY_KEY] = delivery.forFree
        }
    }

    actual override val userUuid: Flow<String?> = context.userUuidDataStore.data.map {
        it[USER_UUID_KEY]
    }

    actual override suspend fun getUserUuid(): String? {
        return userUuid.firstOrNull()
    }

    actual override suspend fun saveUserUuid(userId: String) {
        context.userUuidDataStore.edit {
            it[USER_UUID_KEY] = userId
        }
    }

    actual override suspend fun clearUserUuid() {
        context.userUuidDataStore.edit {
            it.clear()
        }
    }

    actual override val selectedCityUuid: Flow<String?> = context.selectedCityDataStore.data.map {
        it[SELECTED_CITY_UUID_KEY]
    }

    actual override val selectedCityTimeZone: Flow<String> = context.selectedCityDataStore.data.map {
        it[SELECTED_CITY_TIME_ZONE_KEY] ?: DEFAULT_TIME_ZONE
    }

    actual override suspend fun saveSelectedCityUuid(cityUuid: String, cityTimeZone: String) {
        context.selectedCityDataStore.edit {
            it[SELECTED_CITY_UUID_KEY] = cityUuid
            it[SELECTED_CITY_TIME_ZONE_KEY] = cityTimeZone
        }
    }

    actual override suspend fun getSelectedCityUuid(): String? {
        return context.selectedCityDataStore.data.map {
            it[SELECTED_CITY_UUID_KEY]
        }.firstOrNull()
    }

    actual override suspend fun getSelectedCityTimeZone(): String {
        return context.selectedCityDataStore.data.map {
            it[SELECTED_CITY_TIME_ZONE_KEY]
        }.firstOrNull() ?: DEFAULT_TIME_ZONE
    }

    actual override fun observeUserAndCityUuid(): Flow<UserCityUuid> {
        return userUuid.flatMapLatest { userUuid ->
            selectedCityUuid.map { cityUuid ->
                UserCityUuid(
                    userUuid = userUuid ?: "",
                    cityUuid = cityUuid ?: ""
                )
            }
        }
    }

    actual override suspend fun getUserAndCityUuid(): UserCityUuid {
        return UserCityUuid(
            userUuid = getUserUuid() ?: "",
            cityUuid = getSelectedCityUuid() ?: ""
        )
    }

    companion object {
        private const val TOKEN_DATA_STORE = "token data store"
        private const val DELIVERY_DATA_STORE = "delivery data store"
        private const val USER_UUID_DATA_STORE = "user id data store"
        private const val SELECTED_CITY_DATA_STORE = "selected city data store"

        private const val TOKEN = "token"
        private const val USER_UUID = "user uuid"
        private const val DELIVERY_COST = "delivery cost"
        private const val FOR_FREE_DELIVERY = "for free delivery"
        private const val SELECTED_CITY_UUID = "selected city uuid"
        private const val SELECTED_CITY_TIME_ZONE = "selected city time zone"

        private val TOKEN_KEY = stringPreferencesKey(TOKEN)
        private val DELIVERY_COST_KEY = intPreferencesKey(DELIVERY_COST)
        private val FOR_FREE_DELIVERY_KEY = intPreferencesKey(FOR_FREE_DELIVERY)
        private val USER_UUID_KEY = stringPreferencesKey(USER_UUID)
        private val SELECTED_CITY_UUID_KEY = stringPreferencesKey(SELECTED_CITY_UUID)
        private val SELECTED_CITY_TIME_ZONE_KEY = stringPreferencesKey(SELECTED_CITY_TIME_ZONE)

        private const val DEFAULT_DELIVERY_COST = 0
        private const val DEFAULT_FOR_FREE_DELIVERY = 0
        private const val DEFAULT_TIME_ZONE = "UTC+3"
    }
}