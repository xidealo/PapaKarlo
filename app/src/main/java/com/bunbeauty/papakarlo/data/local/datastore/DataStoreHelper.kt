package com.bunbeauty.papakarlo.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.bunbeauty.papakarlo.data.model.ContactInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(private val context: Context) : IDataStoreHelper {

    private val contactInfoDataStore = context.createDataStore(CONTACT_INFO_DATA_STORE)

    override val contactInfo: Flow<ContactInfo> = contactInfoDataStore.data.map {
        Log.d("test", "map ContactInfo")
        ContactInfo(
            it[CONTACT_INFO_ADDRESS_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_START_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_END_KEY] ?: DEFAULT_STRING,
            it[CONTACT_INFO_ADDRESS_PHONE_KEY] ?: DEFAULT_STRING
        )
    }

    override suspend fun saveContactInfo(contactInfo: ContactInfo) {
        Log.d("test", "saveContactInfo")
        contactInfoDataStore.edit {
            it[CONTACT_INFO_ADDRESS_KEY] = contactInfo.address
            it[CONTACT_INFO_ADDRESS_START_KEY] = contactInfo.startTime
            it[CONTACT_INFO_ADDRESS_END_KEY] = contactInfo.endTime
            it[CONTACT_INFO_ADDRESS_PHONE_KEY] = contactInfo.phone
        }
    }

    companion object {
        private const val CONTACT_INFO_DATA_STORE = "contact info data store"
        private const val CONTACT_INFO_ADDRESS = "contact info address"
        private const val CONTACT_INFO_START = "contact info start"
        private const val CONTACT_INFO_END = "contact info end"
        private const val CONTACT_INFO_PHONE = "contact info phone"

        private val CONTACT_INFO_ADDRESS_KEY = preferencesKey<String>(CONTACT_INFO_ADDRESS)
        private val CONTACT_INFO_ADDRESS_START_KEY = preferencesKey<String>(CONTACT_INFO_START)
        private val CONTACT_INFO_ADDRESS_END_KEY = preferencesKey<String>(CONTACT_INFO_END)
        private val CONTACT_INFO_ADDRESS_PHONE_KEY = preferencesKey<String>(CONTACT_INFO_PHONE)

        private const val DEFAULT_STRING = ""
    }
}