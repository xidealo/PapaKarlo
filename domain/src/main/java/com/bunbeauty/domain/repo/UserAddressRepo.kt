package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {

    suspend fun saveUserAddress(userAddress: UserAddress): UserAddress
    suspend fun saveSelectedUserAddress(userAddressUuid: String)
    suspend fun assignToUser(userUuid: String)

    suspend fun getUserAddressByUuid(userAddressUuid: String): UserAddress?
    suspend fun getUserAddressList(): List<UserAddress>

    suspend fun observeSelectedUserAddress(): Flow<UserAddress?>
    fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?>
    fun observeUserAddressList(): Flow<List<UserAddress>>
    fun observeUnassignedUserAddressList(): Flow<List<UserAddress>>
}