package com.example.data_api.repository

import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import com.example.data_api.dao.UserAddressDao
import com.example.domain_api.mapper.IUserAddressMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val userAddressMapper: IUserAddressMapper
): UserAddressRepo {

    override suspend fun saveUserAddress(userAddress: UserAddress) {
        //TODO("Not yet implemented")
    }

    override suspend fun assignToUser(userUuid: String) {
        //TODO("Not yet implemented")
    }

    override fun observeUserAddressByUuid(uuid: String): Flow<UserAddress?> {
        //TODO("Not yet implemented")

        return Any() as Flow<UserAddress?>
    }

    override fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddress>> {
        return userAddressDao.observeUserAddressListByUserUuid(userUuid).map { userAddressList ->
            userAddressList.map(userAddressMapper::toModel)
        }
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        //TODO("Not yet implemented")

        return Any() as Flow<List<UserAddress>>
    }
}