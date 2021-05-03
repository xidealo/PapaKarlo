package com.bunbeauty.domain.repository.address

import androidx.lifecycle.LiveData
import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.AddressDao
import com.bunbeauty.data.mapper.AddressMapper
import com.bunbeauty.data.model.Address
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val addressDao: AddressDao,
    private val apiRepository: IApiRepository,
    private val addressMapper: AddressMapper
) : AddressRepo {

    override suspend fun insert(token: String, address: Address): Long {
        address.uuid = apiRepository.insert(addressMapper.from(address), address.userId ?: "")
        return insert(address)
    }

    override suspend fun insert(address: Address) = addressDao.insert(address)

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