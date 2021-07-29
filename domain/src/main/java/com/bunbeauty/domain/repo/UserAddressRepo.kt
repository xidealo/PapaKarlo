package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.address.UserAddress
import com.bunbeauty.domain.model.firebase.CafeAddressFirebase
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {
    suspend fun insert(token: String, userAddress: UserAddress): UserAddress
    suspend fun insert(userAddress: UserAddress)
    suspend fun insert(userAddressMap: HashMap<String, CafeAddressFirebase>, userUuid: String)

    fun getUserAddressByUuid(uuid: String): Flow<UserAddress?>
    fun getUserAddressListByUserUuid(userId: String): Flow<List<UserAddress>>
    val unassignedUserAddressList: Flow<List<UserAddress>>
}