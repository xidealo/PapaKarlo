package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.USER_ADDRESS_TAG
import com.bunbeauty.data.database.dao.UserAddressDao
import com.bunbeauty.data.database.entity.user.SelectedUserAddressUuidEntity
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val userAddressDao: UserAddressDao,
    private val userAddressMapper: IUserAddressMapper
) : UserAddressRepo {

    override suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress
    ): UserAddress? {
        val userAddressPostServer = userAddressMapper.toPostServerModel(createdUserAddress)
        return apiRepo.postUserAddress(token, userAddressPostServer)
            .handleResultAndReturn(USER_ADDRESS_TAG) { addressServer ->
                val userAddressEntity = userAddressMapper.toEntityModel(addressServer)
                userAddressDao.insert(userAddressEntity)

                userAddressMapper.toModel(addressServer)
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
            addressUuid = addressUuid
        )
        userAddressDao.insertSelectedUserAddressUuid(selectedUserAddressUuid)
    }

    override suspend fun getUserAddressByUuid(userAddressUuid: String): UserAddress? {
        return userAddressDao.getUserAddressByUuid(userAddressUuid)?.let { userAddressEntity ->
            userAddressMapper.toModel(userAddressEntity)
        }
    }

    override fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?> {
        return userAddressDao.observeSelectedUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(userAddressMapper::toModel)
    }

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?> {
        return userAddressDao.observeFirstUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(userAddressMapper::toModel)
    }

    override fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?> {
        return userAddressDao.observeUserAddressByUuid(userAddressUuid)
            .mapFlow(userAddressMapper::toModel)
    }

    override fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddress>> {
        return userAddressDao.observeUserAddressListByUserAndCityUuid(userUuid, cityUuid)
            .mapListFlow(userAddressMapper::toModel)
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        return userAddressDao.observeUnassignedUserAddressList()
            .mapListFlow(userAddressMapper::toModel)
    }
}