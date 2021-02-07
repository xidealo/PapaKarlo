package com.bunbeauty.papakarlo.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(context: Context) : IDataStoreHelper {

    private val deliveryAddressDataStore = context.createDataStore(DELIVERY_ADDRESS_DATA_STORE)
    private val cafeDataStore = context.createDataStore(CAFE_DATA_STORE)
    private val phoneNumberDataStore = context.createDataStore(PHONE_NUMBER_DATA_STORE)
    private val emailDataStore = context.createDataStore(EMAIL_DATA_STORE)

    override val deliveryAddressId: Flow<Long> = deliveryAddressDataStore.data.map {
        it[DELIVERY_ADDRESS_ID_KEY] ?: DEFAULT_LONG
    }

    override suspend fun saveDeliveryAddressId(addressId: Long) {
        deliveryAddressDataStore.edit {
            it[DELIVERY_ADDRESS_ID_KEY] = addressId
        }
    }

    override val cafeId: Flow<String> = cafeDataStore.data.map {
        it[CAFE_ID_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveCafeId(cafeId: String) {
        cafeDataStore.edit {
            it[CAFE_ID_KEY] = cafeId
        }
    }

    override val phoneNumber: Flow<String> = phoneNumberDataStore.data.map {
        it[PHONE_NUMBER_KEY] ?: DEFAULT_STRING
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        phoneNumberDataStore.edit {
            it[PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    override val email: Flow<String> = emailDataStore.data.map {
        it[EMAIL_KEY] ?: DEFAULT_STRING
    }

    override suspend fun saveEmail(email: String) {
        emailDataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    override suspend fun clearData() {
        deliveryAddressDataStore.edit {
            it.clear()
        }
        cafeDataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val DELIVERY_ADDRESS_DATA_STORE = "delivery address data store"
        private const val CAFE_DATA_STORE = "cafe id data store"
        private const val PHONE_NUMBER_DATA_STORE = "phone number data store"
        private const val EMAIL_DATA_STORE = "email data store"

        private const val DELIVERY_ADDRESS_ID = "delivery address id"
        private const val CAFE_ID = "cafe id"
        private const val PHONE_NUMBER = "phone number"
        private const val EMAIL = "email"

        private val DELIVERY_ADDRESS_ID_KEY = longPreferencesKey(DELIVERY_ADDRESS_ID)
        private val CAFE_ID_KEY = stringPreferencesKey(CAFE_ID)
        private val PHONE_NUMBER_KEY = stringPreferencesKey(PHONE_NUMBER)
        private val EMAIL_KEY = stringPreferencesKey(EMAIL)

        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""
    }
}