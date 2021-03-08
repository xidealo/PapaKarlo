package com.bunbeauty.papakarlo.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bunbeauty.papakarlo.data.model.Delivery
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(private val context: Context) : IDataStoreHelper {

    private val Context.deliveryAddressDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_ADDRESS_DATA_STORE)
    private val Context.cafeDataStore: DataStore<Preferences> by preferencesDataStore(name = CAFE_DATA_STORE)
    private val Context.phoneNumberDataStore: DataStore<Preferences> by preferencesDataStore(name = PHONE_NUMBER_DATA_STORE)
    private val Context.emailDataStore: DataStore<Preferences> by preferencesDataStore(name = EMAIL_DATA_STORE)
    private val Context.deliveryDataStore: DataStore<Preferences> by preferencesDataStore(name = DELIVERY_DATA_STORE)

    override val deliveryAddressId: Flow<Long> = context.deliveryAddressDataStore.data.map {
        it[DELIVERY_ADDRESS_ID_KEY] ?: DEFAULT_LONG
    }

    override suspend fun saveDeliveryAddressId(addressId: Long) {
        context.deliveryAddressDataStore.edit {
            it[DELIVERY_ADDRESS_ID_KEY] = addressId
        }
    }

    override val cafeId: Flow<String> = context.cafeDataStore.data.map {
        it[CAFE_ID_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveCafeId(cafeId: String) {
        context.cafeDataStore.edit {
            it[CAFE_ID_KEY] = cafeId
        }
    }

    override val phoneNumber: Flow<String> = context.phoneNumberDataStore.data.map {
        it[PHONE_NUMBER_KEY] ?: DEFAULT_STRING
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        context.phoneNumberDataStore.edit {
            it[PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    override val email: Flow<String> = context.emailDataStore.data.map {
        it[EMAIL_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveEmail(email: String) {
        context.emailDataStore.edit {
            it[EMAIL_KEY] = email
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

    override suspend fun clearData() {
        context.deliveryAddressDataStore.edit {
            it.clear()
        }
        context.cafeDataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val DELIVERY_ADDRESS_DATA_STORE = "delivery address data store"
        private const val CAFE_DATA_STORE = "cafe id data store"
        private const val PHONE_NUMBER_DATA_STORE = "phone number data store"
        private const val EMAIL_DATA_STORE = "email data store"
        private const val DELIVERY_DATA_STORE = "delivery data store"

        private const val DELIVERY_ADDRESS_ID = "delivery address id"
        private const val CAFE_ID = "cafe id"
        private const val PHONE_NUMBER = "phone number"
        private const val EMAIL = "email"
        private const val DELIVERY_COST = "delivery cost"
        private const val FOR_FREE_DELIVERY = "for free delivery"

        private val DELIVERY_ADDRESS_ID_KEY = longPreferencesKey(DELIVERY_ADDRESS_ID)
        private val CAFE_ID_KEY = stringPreferencesKey(CAFE_ID)
        private val PHONE_NUMBER_KEY = stringPreferencesKey(PHONE_NUMBER)
        private val EMAIL_KEY = stringPreferencesKey(EMAIL)
        private val DELIVERY_COST_KEY = intPreferencesKey(DELIVERY_COST)
        private val FOR_FREE_DELIVERY_KEY = intPreferencesKey(FOR_FREE_DELIVERY)

        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""
        private const val DEFAULT_DELIVERY_COST = 0
        private const val DEFAULT_FOR_FREE_DELIVERY = 0
    }
}