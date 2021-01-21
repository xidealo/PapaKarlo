package com.bunbeauty.papakarlo.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.bunbeauty.papakarlo.data.model.ContactInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(context: Context) : IDataStoreHelper {

    private val contactInfoDataStore = context.createDataStore(CONTACT_INFO_DATA_STORE)

    override val contactInfo: Flow<ContactInfo> = contactInfoDataStore.data.map {
        ContactInfo(
            it[CONTACT_INFO_ADDRESS_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_START_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_END_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_PHONE_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_LABEL_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_LATITUDE_KEY] ?: DEFAULT_DOUBLE,
            it[CONTACT_INFO_ADDRESS_LONGITUDE_KEY] ?: DEFAULT_DOUBLE
        )
    }

    override suspend fun saveContactInfo(contactInfo: ContactInfo) {
        contactInfoDataStore.edit {
            it[CONTACT_INFO_ADDRESS_KEY] = contactInfo.address
            it[CONTACT_INFO_ADDRESS_START_KEY] = contactInfo.startTime
            it[CONTACT_INFO_ADDRESS_END_KEY] = contactInfo.endTime
            it[CONTACT_INFO_ADDRESS_PHONE_KEY] = contactInfo.phone
            it[CONTACT_INFO_ADDRESS_LABEL_KEY] = contactInfo.label
            it[CONTACT_INFO_ADDRESS_LATITUDE_KEY] = contactInfo.latitude
            it[CONTACT_INFO_ADDRESS_LONGITUDE_KEY] = contactInfo.longitude
        }
    }

    companion object {
        private const val CONTACT_INFO_DATA_STORE = "contact info data store"
        private const val CONTACT_INFO_ADDRESS = "contact info address"
        private const val CONTACT_INFO_START = "contact info start"
        private const val CONTACT_INFO_END = "contact info end"
        private const val CONTACT_INFO_PHONE = "contact info phone"
        private const val CONTACT_INFO_LABEL = "contact info label"
        private const val CONTACT_INFO_LATITUDE = "contact info latitude"
        private const val CONTACT_INFO_LONGITUDE = "contact info longitude"

        private val CONTACT_INFO_ADDRESS_KEY = stringPreferencesKey(CONTACT_INFO_ADDRESS)
        private val CONTACT_INFO_ADDRESS_START_KEY = stringPreferencesKey(CONTACT_INFO_START)
        private val CONTACT_INFO_ADDRESS_END_KEY = stringPreferencesKey(CONTACT_INFO_END)
        private val CONTACT_INFO_ADDRESS_PHONE_KEY = stringPreferencesKey(CONTACT_INFO_PHONE)
        private val CONTACT_INFO_ADDRESS_LABEL_KEY = stringPreferencesKey(CONTACT_INFO_LABEL)
        private val CONTACT_INFO_ADDRESS_LATITUDE_KEY = doublePreferencesKey(CONTACT_INFO_LATITUDE)
        private val CONTACT_INFO_ADDRESS_LONGITUDE_KEY =
            doublePreferencesKey(CONTACT_INFO_LONGITUDE)

        private const val DEFAULT_STRING = ""
        private const val DEFAULT_DOUBLE = 0.0
    }
}