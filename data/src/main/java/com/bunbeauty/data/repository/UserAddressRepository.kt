package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.USER_ADDRESS_TAG
import com.bunbeauty.data.dao.user_address.IUserAddressDao
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import database.SelectedUserAddressUuidEntity
import kotlinx.coroutines.flow.Flow

class UserAddressRepository  constructor(
    private val apiRepo: ApiRepo,
    private val userAddressDao: IUserAddressDao,
    private val userAddressMapper: IUserAddressMapper
) : UserAddressRepo {

    override suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress
    ): UserAddress? {
        val userAddressPostServer = userAddressMapper.toUserAddressPostServer(createdUserAddress)
        return apiRepo.postUserAddress(token, userAddressPostServer)
            .handleResultAndReturn(USER_ADDRESS_TAG) { addressServer ->
                val userAddressEntity = userAddressMapper.toUserAddressEntity(addressServer)
                userAddressDao.insertUserAddress(userAddressEntity)

                userAddressMapper.toUserAddress(addressServer)
            }
    }

    override suspend fun saveSelectedUserAddress(
        addressUuid: String,
        userUuid: String,
        cityUuid: String
    ) {
        val selectedUserAddressUuid = SelectedUserAddressUuidEntity(
            userUuid = userUuid,
            cityUuid = cityUuid,
            userAddressUuid = addressUuid
        )
        userAddressDao.insertSelectedUserAddressUuid(selectedUserAddressUuid)
    }

    override fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?> {
        return userAddressDao.observeSelectedUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(userAddressMapper::toUserAddress)
    }

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?> {
        return userAddressDao.observeFirstUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(userAddressMapper::toUserAddress)
    }

    override fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddress>> {
        return userAddressDao.observeUserAddressListByUserAndCityUuid(userUuid, cityUuid)
            .mapListFlow(userAddressMapper::toUserAddress)
    }
}