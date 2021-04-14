package com.bunbeauty.domain.repository.address

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepo {
    suspend fun insert(address: Address): Long
    suspend fun update(address: Address)
    fun getAddresses(): LiveData<List<Address>>
    fun getCafeAddresses(): LiveData<List<Address>>
    fun getNotCafeAddresses(): LiveData<List<Address>>
    fun getAddressById(id: Long): LiveData<Address?>
    fun getAddressByCafeId(cafeId: String): Flow<Address?>
    fun getFirstAddress(): LiveData<Address?>
}