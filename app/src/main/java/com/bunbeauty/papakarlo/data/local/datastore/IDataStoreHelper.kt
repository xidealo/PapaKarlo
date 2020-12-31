package com.bunbeauty.papakarlo.data.local.datastore

import com.bunbeauty.papakarlo.data.model.ContactInfo
import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {

    val contactInfo: Flow<ContactInfo>
    suspend fun saveContactInfo(contactInfo: ContactInfo)
}