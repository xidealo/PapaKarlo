package com.bunbeauty.data.utils

import com.bunbeauty.data.model.Delivery
import kotlinx.coroutines.flow.Flow

interface IDataStoreHelper {

    val deliveryAddressId: Flow<Long>
    suspend fun saveDeliveryAddressId(addressId: Long)

    val cafeId: Flow<String>
    suspend fun saveCafeId(cafeId: String)

    val delivery: Flow<Delivery>
    suspend fun saveDelivery(delivery: Delivery)

    val userId: Flow<String>
    suspend fun saveUserId(userId: String)

    suspend fun clearData()
}