package com.bunbeauty.shared.data.dao.user_address

import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.db.UserAddressEntity
import kotlinx.coroutines.flow.Flow

interface IUserAddressDao {

    suspend fun insertUserAddress(userAddress: UserAddressEntity)
    suspend fun insertUserAddressList(userAddressList: List<UserAddressEntity>)
    suspend fun insertSelectedUserAddressUuid(selectedUserAddressUuidEntity: SelectedUserAddressUuidEntity)

    suspend fun getUserAddressCountByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Long

    suspend fun getSelectedUserAddressByUserAndCityUuid(userUuid: String, cityUuid: String): UserAddressEntity?
    suspend fun getUserAddressListByUserAndCityUuid(userUuid: String, cityUuid: String): List<UserAddressEntity>
    suspend fun geFirstUserAddressByUserAndCityUuid(userUuid: String, cityUuid: String): UserAddressEntity?

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