package com.bunbeauty.papakarlo.data.local.datastore

import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {
    val selectedDeliveryAddress:Flow<Address>
    suspend fun saveSelectedDeliveryAddress(address: Address)

    val selectedPickupAddress:Flow<Address>
    suspend fun saveSelectedPickupAddress(address: Address)

    suspend fun clearData()
}