package com.bunbeauty.data.repository

import androidx.lifecycle.LiveData
import com.bunbeauty.data.dao.CafeAddressDao
import com.bunbeauty.domain.model.local.address.CafeAddress
import com.bunbeauty.domain.repo.CafeAddressRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CafeAddressRepository @Inject constructor(
    private val cafeAddressDao: CafeAddressDao
) : CafeAddressRepo {

    override suspend fun insert(cafeAddress: CafeAddress) {
        cafeAddressDao.insert(cafeAddress)
    }

    override suspend fun update(cafeAddress: CafeAddress) {
        cafeAddressDao.update(cafeAddress)
    }

    override fun getCafeAddresses(): Flow<List<CafeAddress>> {
        return cafeAddressDao.getCafeAddresses()
    }

    override fun getCafeAddressById(id: Long): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressById(id)
    }

    override fun getCafeAddressByCafeUuid(cafeId: String): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressByCafeId(cafeId)
    }

    override fun getFirstAddress(): LiveData<CafeAddress?> {
        return cafeAddressDao.getFirstAddress()
    }

    override fun getCafeAddressByUuid(uuid: String): Flow<CafeAddress?> {
        return cafeAddressDao.getAddressByCafeUuid(uuid)
    }

}