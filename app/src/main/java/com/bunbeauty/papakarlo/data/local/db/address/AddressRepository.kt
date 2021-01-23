package com.bunbeauty.papakarlo.data.local.db.address

import com.bunbeauty.papakarlo.data.model.Address
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
}