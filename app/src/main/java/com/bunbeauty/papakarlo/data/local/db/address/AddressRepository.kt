package com.bunbeauty.papakarlo.data.local.db.address

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val addressDao: AddressDao
) : AddressRepo {

    override suspend fun insert(address: Address): Long {
        return addressDao.insert(address)
    }

    override suspend fun update(address: Address) {
        addressDao.update(address)
    }

    override fun getAddresses(): LiveData<List<Address>> {
        return addressDao.getAddresses()
    }

    override fun getCafeAddresses(): LiveData<List<Address>> {
        return addressDao.getCafeAddresses()
    }

    override fun getNotCafeAddresses(): LiveData<List<Address>> {
        return addressDao.getNotCafeAddresses()
    }

    override fun getAddress(id: Long): Flow<Address?> {
        return addressDao.getAddress(id)
    }
}