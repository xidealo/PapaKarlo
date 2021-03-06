package com.bunbeauty.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val context: Context) : DataStoreRepo {

    private val Context.deliveryAddressDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_ADDRESS_DATA_STORE)
    private val Context.cafeDataStore: DataStore<Preferences> by preferencesDataStore(name = CAFE_DATA_STORE)
    private val Context.deliveryDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_DATA_STORE)
    private val Context.userId: DataStore<Preferences> by preferencesDataStore(name = USER_ID_DATA_STORE)

    /**
     * if user didnt login
     */
    private val Context.phone: DataStore<Preferences> by preferencesDataStore(name = PHONE_DATA_STORE)
    private val Context.email: DataStore<Preferences> by preferencesDataStore(name = EMAIL_DATA_STORE)

    override val deliveryAddressId: Flow<String> = context.deliveryAddressDataStore.data.map {
        it[DELIVERY_ADDRESS_ID_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveDeliveryAddressId(addressId: String) {
        context.deliveryAddressDataStore.edit {
            it[DELIVERY_ADDRESS_ID_KEY] = addressId
        }
    }

    override val cafeAddressId: Flow<String> = context.cafeDataStore.data.map {
        it[CAFE_ID_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveCafeAddressId(addressId: String) {
        context.cafeDataStore.edit {
            it[CAFE_ID_KEY] = addressId
        }
    }

    override val delivery = context.deliveryDataStore.data.map {
        Delivery(
            it[DELIVERY_COST_KEY] ?: DEFAULT_DELIVERY_COST,
            it[FOR_FREE_DELIVERY_KEY] ?: DEFAULT_FOR_FREE_DELIVERY
        )
    }

    override suspend fun saveDelivery(delivery: Delivery) {
        context.deliveryDataStore.edit {
            it[DELIVERY_COST_KEY] = delivery.cost
            it[FOR_FREE_DELIVERY_KEY] = delivery.forFree
        }
    }

    override val userId: Flow<String> = context.userId.data.map {
        it[USER_ID_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveUserId(userId: String) {
        context.userId.edit {
            it[USER_ID_KEY] = userId
        }
    }

    override val phone: Flow<String> = context.phone.data.map {
        it[PHONE_KEY] ?: DEFAULT_STRING
    }

    override suspend fun savePhone(phone: String) {
        context.phone.edit {
            it[PHONE_KEY] = phone
        }
    }

    override val email: Flow<String> = context.email.data.map {
        it[EMAIL_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveEmail(email: String) {
        context.email.edit {
            it[EMAIL_KEY] = email
        }
    }

    override suspend fun clearData() {
        context.deliveryAddressDataStore.edit {
            it.clear()
        }
        context.cafeDataStore.edit {
            it.clear()
        }
        context.userId.edit {
            it.clear()
        }
    }

    companion object {
        private const val DELIVERY_ADDRESS_DATA_STORE = "delivery address data store"
        private const val CAFE_DATA_STORE = "cafe id data store"
        private const val DELIVERY_DATA_STORE = "delivery data store"
        private const val USER_ID_DATA_STORE = "user id data store"

        private const val PHONE_DATA_STORE = "phone data store"
        private const val EMAIL_DATA_STORE = "email data store"

        private const val DELIVERY_ADDRESS_ID = "delivery address id"
        private const val CAFE_ID = "cafe id"
        private const val DELIVERY_COST = "delivery cost"
        private const val FOR_FREE_DELIVERY = "for free delivery"
        private const val USER_ID = "user id"

        private const val PHONE = "phone"
        private const val EMAIL = "email"

        private val DELIVERY_ADDRESS_ID_KEY = stringPreferencesKey(DELIVERY_ADDRESS_ID)
        private val CAFE_ID_KEY = stringPreferencesKey(CAFE_ID)
        private val DELIVERY_COST_KEY = intPreferencesKey(DELIVERY_COST)
        private val FOR_FREE_DELIVERY_KEY = intPreferencesKey(FOR_FREE_DELIVERY)
        private val USER_ID_KEY = stringPreferencesKey(USER_ID)
        private val PHONE_KEY = stringPreferencesKey(PHONE)
        private val EMAIL_KEY = stringPreferencesKey(EMAIL)

        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""
        private const val DEFAULT_DELIVERY_COST = 0
        private const val DEFAULT_FOR_FREE_DELIVERY = 0
    }
}