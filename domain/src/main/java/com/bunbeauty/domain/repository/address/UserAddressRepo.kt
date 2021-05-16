package com.bunbeauty.domain.repository.address

import com.bunbeauty.data.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {
    suspend fun insert(token: String, userAddress: UserAddress): UserAddress
    suspend fun insert(userAddress: UserAddress): Long

    fun getUserAddressByUuid(uuid: String): Flow<UserAddress?>
    fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>>
}