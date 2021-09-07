package com.example.data_api.repository

import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepository @Inject constructor(): UserAddressRepo {

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
        //TODO("Not yet implemented")

        return Any() as Flow<List<UserAddress>>
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        //TODO("Not yet implemented")

        return Any() as Flow<List<UserAddress>>
    }
}