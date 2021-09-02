package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.entity.address.UserAddressEntity
import com.bunbeauty.domain.model.ui.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {

    suspend fun saveUserAddress(userAddress: UserAddress)
    suspend fun saveUserAddresses(userAddressList: List<UserAddressEntity>)
    suspend fun assignToUser(userUuid: String)

    fun observeUserAddressByUuid(uuid: String): Flow<UserAddress?>
    fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddress>>
    fun observeUnassignedUserAddressList(): Flow<List<UserAddress>>
}