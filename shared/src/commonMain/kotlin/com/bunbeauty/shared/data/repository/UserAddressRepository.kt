package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.model.address.UserAddressCache
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.core.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

open class UserAddressRepository(
    private val networkConnector: NetworkConnector,
    private val userAddressDao: IUserAddressDao,
    private val userAddressMapper: UserAddressMapper,
    private val dataStoreRepo: DataStoreRepo,
) : BaseRepository(),
    UserAddressRepo {
    override val tag: String = "USER_ADDRESS_TAG"

    private var userAddressCache: UserAddressCache? = null

    override suspend fun saveUserAddress(
        createdUserAddress: CreatedUserAddress,
    ): UserAddress? {
        val token = dataStoreRepo.getToken() ?: throw NoTokenException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()

        val userAddressPostServer =
            userAddressMapper.toUserAddressPostServer(
                createdUserAddress = createdUserAddress,
                cityUuid = cityUuid
            )

        return networkConnector
            .postUserAddress(token, userAddressPostServer)
            .getNullableResult { addressServer ->
                val userAddressEntity = userAddressMapper.toUserAddressEntity(addressServer)
                userAddressDao.insertUserAddress(userAddressEntity)

                val userAddress = userAddressMapper.toUserAddress(addressServer)
                userAddressCache?.let { cache ->
                    userAddressCache =
                        cache.copy(
                            userAddressList = cache.userAddressList + userAddress,
                        )
                }

                userAddress
            }
    }

    override suspend fun saveSelectedUserAddress(
        addressUuid: String,
    ) {
        val userCityUuid = dataStoreRepo.getUserAndCityUuid()

        val selectedUserAddressUuid =
            SelectedUserAddressUuidEntity(
                userUuid = userCityUuid.userUuid,
                cityUuid = userCityUuid.cityUuid,
                userAddressUuid = addressUuid,
            )

        userAddressDao.insertSelectedUserAddressUuid(selectedUserAddressUuid)
    }

    override suspend fun getSelectedAddressByUserAndCityUuid(): UserAddress? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null

        return userAddressDao
            .getSelectedUserAddressByUserAndCityUuid(userUuid, cityUuid)
            ?.let { userAddressEntity ->
                userAddressMapper.toUserAddress(userAddressEntity)
            }
    }

    override suspend fun getFirstUserAddressByUserAndCityUuid(): UserAddress? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null

        return userAddressDao
            .geFirstUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            )?.let { userAddressEntity ->
                userAddressMapper.toUserAddress(userAddressEntity)
            } ?: dataStoreRepo.getToken()?.let { token ->
            getUserAddressListByUserAndCityUuid().firstOrNull()
        }
    }

    override suspend fun getUserAddressListByUserAndCityUuid(): List<UserAddress> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return emptyList()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        val token = dataStoreRepo.getToken() ?: return emptyList()

        val cache = userAddressCache
        return if (cache != null &&
            cache.userUuid == userUuid &&
            cache.cityUuid == cityUuid
        ) {
            cache.userAddressList
        } else {
            networkConnector.getUserAddressListByCityUuid(token, cityUuid).getListResult(
                onError = {
                    userAddressDao
                        .getUserAddressListByUserAndCityUuid(userUuid, cityUuid)
                        .map(userAddressMapper::toUserAddress)
                },
                onSuccess = { userAddressSeverList ->
                    userAddressDao.insertUserAddressList(
                        userAddressSeverList.map(userAddressMapper::toUserAddressEntity),
                    )
                    userAddressSeverList
                        .map(userAddressMapper::toUserAddress)
                        .also { userAddressList ->
                            userAddressCache =
                                UserAddressCache(
                                    userAddressList = userAddressList,
                                    userUuid = userUuid,
                                    cityUuid = cityUuid,
                                )
                        }
                },
            )
        }
    }

    override suspend fun observeSelectedUserAddressByUserAndCityUuid(): Flow<UserAddress?> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return flowOf(null)
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return flowOf(null)

        return userAddressDao
            .observeSelectedUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).mapFlow(userAddressMapper::toUserAddress)
    }

    override suspend fun observeFirstUserAddressByUserAndCityUuid(): Flow<UserAddress?> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return flowOf(null)
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return flowOf(null)

        return userAddressDao
            .observeFirstUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).mapFlow(userAddressMapper::toUserAddress)
    }


    override fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<List<UserAddress>> =
        userAddressDao
            .observeUserAddressListByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).mapListFlow(userAddressMapper::toUserAddress)

    override suspend fun clearCache() {
        userAddressDao.deleteAll()
    }
}
