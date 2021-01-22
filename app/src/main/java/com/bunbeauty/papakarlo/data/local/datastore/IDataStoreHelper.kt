package com.bunbeauty.papakarlo.data.local.datastore

import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {
    val selectedAddress:Flow<Address>
    suspend fun saveSelectedAddress(address: Address)
}