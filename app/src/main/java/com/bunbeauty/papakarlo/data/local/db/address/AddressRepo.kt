package com.bunbeauty.papakarlo.data.local.db.address

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.Address

interface AddressRepo {
    suspend fun insert(address: Address): Long
    suspend fun update(address: Address)
    fun getAddresses(): LiveData<List<Address>>
    fun getCafeAddresses(): LiveData<List<Address>>
    fun getNotCafeAddresses(): LiveData<List<Address>>
    fun getAddressById(id: Long): LiveData<Address?>
    fun getAddressByCafeId(cafeId: String): LiveData<Address?>
    fun getFirstAddress(): LiveData<Address?>
}