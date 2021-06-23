package com.bunbeauty.domain.repository.address

import com.bunbeauty.data.model.address.UserAddress
import com.bunbeauty.data.model.firebase.AddressFirebase
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {
    suspend fun insert(token: String, userAddress: UserAddress): UserAddress
    suspend fun insert(userAddress: UserAddress): Long
    suspend fun insert(userAddressMap: HashMap<String, AddressFirebase>, userUuid: String)

    fun getUserAddressByUuid(uuid: String): Flow<UserAddress?>
    fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>>
}