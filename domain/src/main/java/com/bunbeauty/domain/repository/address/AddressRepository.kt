package com.bunbeauty.domain.repository.address

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.Address
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

    override fun getAddressById(id: Long): Flow<Address?> {
        return addressDao.getAddressById(id)
    }

    override fun getAddressByCafeId(cafeId: String): Flow<Address?> {
        return addressDao.getAddressByCafeId(cafeId)
    }

    override fun getFirstAddress(): LiveData<Address?> {
        return addressDao.getFirstAddress()
    }
}