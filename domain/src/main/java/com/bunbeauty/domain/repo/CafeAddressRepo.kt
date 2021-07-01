package com.bunbeauty.domain.repo

import androidx.lifecycle.LiveData
import com.bunbeauty.domain.model.local.address.CafeAddress
import kotlinx.coroutines.flow.Flow

interface CafeAddressRepo {
    //TODO remove livedata to flow
    suspend fun insert(cafeAddress: CafeAddress): Long
    suspend fun update(cafeAddress: CafeAddress)
    fun getCafeAddresses(): Flow<List<CafeAddress>>
    fun getCafeAddressById(id: Long): Flow<CafeAddress?>
    fun getCafeAddressByUuid(uuid: String): Flow<CafeAddress?>
    fun getCafeAddressByCafeId(cafeId: String): Flow<CafeAddress?>
    fun getFirstAddress(): LiveData<CafeAddress?>
}