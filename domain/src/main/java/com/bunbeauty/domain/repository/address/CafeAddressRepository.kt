package com.bunbeauty.domain.repository.address

import androidx.lifecycle.LiveData
import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.CafeAddressDao
import com.bunbeauty.data.mapper.AddressMapper
import com.bunbeauty.data.model.address.CafeAddress
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CafeAddressRepository @Inject constructor(
    private val cafeAddressDao: CafeAddressDao
) : CafeAddressRepo {

    override suspend fun insert(cafeAddress: CafeAddress) = cafeAddressDao.insert(cafeAddress)

    override suspend fun update(cafeAddress: CafeAddress) {
        cafeAddressDao.update(cafeAddress)
    }

    override fun getCafeAddresses(): Flow<List<CafeAddress>> {
        return cafeAddressDao.getCafeAddresses()
    }

    override fun getCafeAddressById(id: Long): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressById(id)
    }

    override fun getCafeAddressByCafeId(cafeId: String): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressByCafeId(cafeId)
    }

    override fun getFirstAddress(): LiveData<CafeAddress?> {
        return cafeAddressDao.getFirstAddress()
    }

    override fun getCafeAddressByUuid(uuid: String): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressByCafeUuid(uuid)
    }

}