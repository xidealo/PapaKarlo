package com.bunbeauty.data.dao.user_address

import database.SelectedUserAddressUuidEntity
import database.UserAddressEntity
import kotlinx.coroutines.flow.Flow

interface IUserAddressDao {

    suspend fun insertUserAddress(userAddress: UserAddressEntity)
    suspend fun insertUserAddressList(userAddressList: List<UserAddressEntity>)
    suspend fun insertSelectedUserAddressUuid(selectedUserAddressUuidEntity: SelectedUserAddressUuidEntity)

    suspend fun getUserAddressCountByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Long

    fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?>

    fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?>

    fun observeUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddressEntity>>

}