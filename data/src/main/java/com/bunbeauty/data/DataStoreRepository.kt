package com.bunbeauty.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val context: Context) : DataStoreRepo {

    private val Context.userAddressDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_ADDRESS_DATA_STORE)
    private val Context.cafeDataStore: DataStore<Preferences> by preferencesDataStore(name = CAFE_DATA_STORE)
    private val Context.deliveryDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_DATA_STORE)
    private val Context.userUuidDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_UUID_DATA_STORE)
    private val Context.deferredTimeDataStore: DataStore<Preferences> by preferencesDataStore(name = DEFERRED_TIME_DATA_STORE)
    private val Context.selectedCityDataStore: DataStore<Preferences> by preferencesDataStore(name = SELECTED_CITY_DATA_STORE)

    override val userAddressUuid: Flow<String?> = context.userAddressDataStore.data.map {
        it[DELIVERY_ADDRESS_UUID_KEY]
    }

    override suspend fun saveUserAddressUuid(addressId: String) {
        withContext(IO) {
            context.userAddressDataStore.edit {
                it[DELIVERY_ADDRESS_UUID_KEY] = addressId
            }
        }
    }

    override suspend fun clearUserAddressUuid() {
        withContext(IO) {
            context.userAddressDataStore.edit { dataStore ->
                dataStore.clear()
            }
        }
    }

    override val cafeAddressUuid: Flow<String?> = context.cafeDataStore.data.map {
        it[CAFE_UUID_KEY]
    }

    override suspend fun saveCafeAddressUuid(addressId: String) {
        withContext(IO) {
            context.cafeDataStore.edit {
                it[CAFE_UUID_KEY] = addressId
            }
        }
    }

    override val delivery = context.deliveryDataStore.data.map {
        Delivery(
            it[DELIVERY_COST_KEY] ?: DEFAULT_DELIVERY_COST,
            it[FOR_FREE_DELIVERY_KEY] ?: DEFAULT_FOR_FREE_DELIVERY
        )
    }

    override suspend fun saveDelivery(delivery: Delivery) {
        withContext(IO) {
            context.deliveryDataStore.edit {
                it[DELIVERY_COST_KEY] = delivery.cost
                it[FOR_FREE_DELIVERY_KEY] = delivery.forFree
            }
        }
    }

    override val userUuid: Flow<String?> = context.userUuidDataStore.data.map {
        it[USER_UUID_KEY]
    }

    override suspend fun saveUserUuid(userId: String) {
        withContext(IO) {
            context.userUuidDataStore.edit {
                it[USER_UUID_KEY] = userId
            }
        }
    }

    override val deferredTime: Flow<String?> = context.deferredTimeDataStore.data.map {
        it[DEFERRED_TIME_KEY]
    }

    override suspend fun saveDeferredTime(deferredTime: String) {
        withContext(IO) {
            context.deferredTimeDataStore.edit {
                it[DEFERRED_TIME_KEY] = deferredTime
            }
        }
    }

    override val selectedCityUuid: Flow<String?> = context.selectedCityDataStore.data.map {
        it[SELECTED_CITY_UUID_KEY]
    }

    override suspend fun saveSelectedCityUuid(cityUuid: String) {
        withContext(IO) {
            context.selectedCityDataStore.edit {
                it[SELECTED_CITY_UUID_KEY] = cityUuid
            }
        }
    }

    override suspend fun getSelectedCityUuid(): String? {
        return context.selectedCityDataStore.data.map {
            it[SELECTED_CITY_UUID_KEY]
        }.firstOrNull()
    }

    override suspend fun clearData() {
        context.userAddressDataStore.edit {
            it.clear()
        }
        context.cafeDataStore.edit {
            it.clear()
        }
        context.userUuidDataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val USER_ADDRESS_DATA_STORE = "delivery address data store"
        private const val CAFE_DATA_STORE = "cafe id data store"
        private const val DELIVERY_DATA_STORE = "delivery data store"
        private const val USER_UUID_DATA_STORE = "user id data store"
        private const val DEFERRED_TIME_DATA_STORE = "deferred time data store"
        private const val SELECTED_CITY_DATA_STORE = "selected city data store"

        private const val DELIVERY_ADDRESS_UUID = "delivery address uuid"
        private const val CAFE_UUID = "cafe uuid"
        private const val DELIVERY_COST = "delivery cost"
        private const val FOR_FREE_DELIVERY = "for free delivery"
        private const val USER_UUID = "user uuid"
        private const val DEFERRED_TIME = "deferred time"
        private const val SELECTED_CITY_UUID = "selected city uuid"

        private val DELIVERY_ADDRESS_UUID_KEY = stringPreferencesKey(DELIVERY_ADDRESS_UUID)
        private val CAFE_UUID_KEY = stringPreferencesKey(CAFE_UUID)
        private val DELIVERY_COST_KEY = intPreferencesKey(DELIVERY_COST)
        private val FOR_FREE_DELIVERY_KEY = intPreferencesKey(FOR_FREE_DELIVERY)
        private val USER_UUID_KEY = stringPreferencesKey(USER_UUID)
        private val DEFERRED_TIME_KEY = stringPreferencesKey(DEFERRED_TIME)
        private val SELECTED_CITY_UUID_KEY = stringPreferencesKey(SELECTED_CITY_UUID)

        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""
        private const val DEFAULT_DELIVERY_COST = 0
        private const val DEFAULT_FOR_FREE_DELIVERY = 0
    }
}