package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {

    suspend fun saveUserAddress(token: String, createdUserAddress: CreatedUserAddress): UserAddress?
    suspend fun saveSelectedUserAddress(addressUuid: String, userUuid: String, cityUuid: String)

    suspend fun getUserAddressByUuid(userAddressUuid: String): UserAddress?

    fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?>

    fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?>

    fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?>
    fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddress>>

    fun observeUnassignedUserAddressList(): Flow<List<UserAddress>>
}