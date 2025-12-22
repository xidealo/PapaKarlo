package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepo {
    suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress,
    ): UserAddress?

    suspend fun saveSelectedUserAddress(
        addressUuid: String,
        userUuid: String,
        cityUuid: String,
    )

    suspend fun getSelectedAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): UserAddress?

    suspend fun getFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): UserAddress?

    suspend fun getUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String,
    ): List<UserAddress>

    fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<UserAddress?>

    fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<UserAddress?>

    fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<List<UserAddress>>

    suspend fun clearCache()
}
