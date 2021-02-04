package com.bunbeauty.papakarlo.data.local.db.address

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepo {
    suspend fun insert(address: Address): Long
    suspend fun update(address: Address)
    fun getAddresses(): LiveData<List<Address>>
    fun getCafeAddresses(): LiveData<List<Address>>
    fun getNotCafeAddresses(): LiveData<List<Address>>
    fun getAddress(id: Long): Flow<Address?>
}