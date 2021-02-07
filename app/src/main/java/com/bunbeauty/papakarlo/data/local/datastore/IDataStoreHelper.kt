package com.bunbeauty.papakarlo.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {

    val deliveryAddressId: Flow<Long>
    suspend fun saveDeliveryAddressId(addressId: Long)

    val cafeId: Flow<String>
    suspend fun saveCafeId(cafeId: String)

    val phoneNumber: Flow<String>
    suspend fun savePhoneNumber(phoneNumber: String)

    val email: Flow<String>
    suspend fun saveEmail(email: String)

    suspend fun clearData()
}