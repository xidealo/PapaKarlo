package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.address.UserAddressCache
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow

open class UserAddressRepository(
    private val networkConnector: NetworkConnector,
    private val userAddressDao: IUserAddressDao,
    private val userAddressMapper: UserAddressMapper,
    private val dataStoreRepo: DataStoreRepo
) : BaseRepository(), UserAddressRepo {

    override val tag: String = "USER_ADDRESS_TAG"

    private var userAddressCache: UserAddressCache? = null

    override suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress
    ): UserAddress? {
        val userAddressPostServer = userAddressMapper.toUserAddressPostServer(createdUserAddress)
        return networkConnector.postUserAddress(token, userAddressPostServer)
            .getNullableResult { addressServer ->
                val userAddressEntity = userAddressMapper.toUserAddressEntity(addressServer)
                userAddressDao.insertUserAddress(userAddressEntity)

                val userAddress = userAddressMapper.toUserAddress(addressServer)
                userAddressCache?.let { cache ->
                    userAddressCache = cache.copy(
                        userAddressList = cache.userAddressList + userAddress
                    )
                }

                userAddress
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

    override suspend fun getSelectedAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): UserAddress? {
        return userAddressDao.getSelectedUserAddressByUserAndCityUuid(userUuid, cityUuid)
            ?.let { userAddressEntity ->
                userAddressMapper.toUserAddress(userAddressEntity)
            }
    }

    override suspend fun getFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): UserAddress? {
        return userAddressDao.geFirstUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        )?.let { userAddressEntity ->
            userAddressMapper.toUserAddress(userAddressEntity)
        } ?: dataStoreRepo.getToken()?.let { token ->
            getUserAddressListByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
                token = token
            ).firstOrNull()
        }
    }

    override suspend fun getUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String
    ): List<UserAddress> {
        val cache = userAddressCache
        return if (cache != null &&
            cache.userUuid == userUuid &&
            cache.cityUuid == cityUuid
        ) {
            cache.userAddressList
        } else {
            networkConnector.getUserAddressListByCityUuid(token, cityUuid).getListResult(
                onError = {
                    userAddressDao.getUserAddressListByUserAndCityUuid(userUuid, cityUuid)
                        .map(userAddressMapper::toUserAddress)
                },
                onSuccess = { userAddressSeverList ->
                    userAddressDao.insertUserAddressList(
                        userAddressSeverList.map(userAddressMapper::toUserAddressEntity)
                    )
                    userAddressSeverList.map(userAddressMapper::toUserAddress)
                        .also { userAddressList ->
                            userAddressCache = UserAddressCache(
                                userAddressList = userAddressList,
                                userUuid = userUuid,
                                cityUuid = cityUuid
                            )
                        }
                }
            )
        }
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

    override suspend fun clearCache() {
        userAddressDao.deleteAll()
    }
}
