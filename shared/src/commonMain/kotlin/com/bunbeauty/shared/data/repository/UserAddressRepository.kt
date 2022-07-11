package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow

class UserAddressRepository(
    private val networkConnector: NetworkConnector,
    private val userAddressDao: IUserAddressDao,
    private val userAddressMapper: IUserAddressMapper
) : BaseRepository(), UserAddressRepo {

    override val tag: String = "USER_ADDRESS_TAG"

    override suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress
    ): UserAddress? {
        val userAddressPostServer = userAddressMapper.toUserAddressPostServer(createdUserAddress)
        return networkConnector.postUserAddress(token, userAddressPostServer)
            .getNullableResult { addressServer ->
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
        return userAddressDao.observeSelectedUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).mapFlow(userAddressMapper::toUserAddress)
    }

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddress?> {
        return userAddressDao.observeFirstUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).mapFlow(userAddressMapper::toUserAddress)
    }

    override fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddress>> {
        return userAddressDao.observeUserAddressListByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).mapListFlow(userAddressMapper::toUserAddress)
    }
}