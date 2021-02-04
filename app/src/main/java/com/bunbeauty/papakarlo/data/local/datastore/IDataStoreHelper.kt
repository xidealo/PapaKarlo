package com.bunbeauty.papakarlo.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {
    val selectedDeliveryAddress:Flow<Long>
    suspend fun saveSelectedDeliveryAddress(addressId: Long)

    val selectedPickupAddress:Flow<Long>
    suspend fun saveSelectedPickupAddress(addressId: Long)

    suspend fun clearData()
}