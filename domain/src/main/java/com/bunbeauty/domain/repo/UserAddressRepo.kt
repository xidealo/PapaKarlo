package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {

    suspend fun saveUserAddress(userAddress: UserAddress): UserAddress
    suspend fun assignToUser(userUuid: String)

    fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?>
    fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddress>>
    fun observeUnassignedUserAddressList(): Flow<List<UserAddress>>
}