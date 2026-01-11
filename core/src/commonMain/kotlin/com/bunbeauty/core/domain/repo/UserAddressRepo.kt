package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.core.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {
    suspend fun saveUserAddress(
        createdUserAddress: CreatedUserAddress,
    ): UserAddress?

    suspend fun saveSelectedUserAddress(
        addressUuid: String,
    )

    suspend fun getSelectedAddressByUserAndCityUuid(): UserAddress?

    suspend fun getFirstUserAddressByUserAndCityUuid(): UserAddress?

    suspend fun getUserAddressListByUserAndCityUuid(): List<UserAddress>

    suspend fun observeSelectedUserAddressByUserAndCityUuid(): Flow<UserAddress?>

    suspend fun observeFirstUserAddressByUserAndCityUuid(): Flow<UserAddress?>

    fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<List<UserAddress>>

    suspend fun clearCache()
}
