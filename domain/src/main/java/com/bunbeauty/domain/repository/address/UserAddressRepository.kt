package com.bunbeauty.domain.repository.address

import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.UserAddressDao
import com.bunbeauty.data.mapper.AddressMapper
import com.bunbeauty.data.model.address.UserAddress
import com.bunbeauty.data.model.firebase.AddressFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val apiRepository: IApiRepository,
    private val addressMapper: AddressMapper
) : UserAddressRepo {

    override suspend fun insert(token: String, userAddress: UserAddress): UserAddress {
        userAddress.uuid =
            apiRepository.insert(addressMapper.from(userAddress), userAddress.userId ?: "")
        insert(userAddress)
        return userAddress
    }

    override suspend fun insert(userAddress: UserAddress) = userAddressDao.insert(userAddress)

    override suspend fun insert(userAddressMap: HashMap<String, AddressFirebase>, userUuid: String) {
        userAddressMap.forEach { (addressUuid, addressFirebase) ->
            val address = addressMapper.to(addressFirebase).also { it.uuid = addressUuid }
            insert(UserAddress(userId = userUuid).also {
                it.uuid = address.uuid
                it.street = address.street
                it.house = address.house
                it.flat = address.flat
                it.entrance = address.entrance
                it.intercom = address.intercom
                it.floor = address.floor
            } )
        }
    }

    override fun getUserAddressByUuid(uuid: String): Flow<UserAddress?> {
        return userAddressDao.getUserAddressByUuid(uuid)
    }

    override fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>> {
        return userAddressDao.getUserAddressByUserId(userId)
    }
}