package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.UserAddressDao
import com.bunbeauty.data.mapper.firebase.AddressMapper
import com.bunbeauty.domain.model.firebase.AddressFirebase
import com.bunbeauty.domain.model.ui.address.UserAddress
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val apiRepo: ApiRepo,
    private val addressMapper: AddressMapper
) : UserAddressRepo {

    override suspend fun insert(token: String, userAddress: UserAddress): UserAddress {
        userAddress.uuid =
            apiRepo.insert(addressMapper.to(userAddress), userAddress.userUuid ?: "")
        insert(userAddress)
        return userAddress
    }

    override suspend fun insert(userAddress: UserAddress) {
        userAddressDao.insert(userAddress)
    }

    override suspend fun insert(
        userAddressMap: HashMap<String, AddressFirebase>,
        userUuid: String
    ) {
        userAddressMap.forEach { (addressUuid, addressFirebase) ->
            val address = addressMapper.from(addressFirebase).also { it.uuid = addressUuid }
            insert(UserAddress(userUuid = userUuid).also {
                it.uuid = address.uuid
                it.street = address.street
                it.house = address.house
                it.flat = address.flat
                it.entrance = address.entrance
                it.comment = address.comment
                it.floor = address.floor
            })
        }
    }

    override fun getUserAddressByUuid(uuid: String): Flow<UserAddress?> {
        return userAddressDao.getUserAddressByUuid(uuid)
    }

    override fun getUserAddressListByUserUuid(userId: String): Flow<List<UserAddress>> {
        return userAddressDao.getUserAddressByUserId(userId)
    }

    override val unassignedUserAddressList: Flow<List<UserAddress>>
        get() = userAddressDao.getUnassignedList()
}