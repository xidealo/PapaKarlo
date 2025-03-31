package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.user.IUserDao
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.profile.IProfileMapper
import com.bunbeauty.shared.data.mapper.user.IUserMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val networkConnector: NetworkConnector,
    private val profileMapper: IProfileMapper,
    private val userMapper: IUserMapper,
    private val userDao: IUserDao,
    private val userAddressDao: IUserAddressDao,
    private val orderDao: IOrderDao,
    private val dataStoreRepo: DataStoreRepo
) : DatabaseCacheRepository(), UserRepo {

    override val tag: String = "USER_TAG"
    var cachedUserUuid: String? = null

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(uuid = userUuid).mapFlow(userMapper::toUser)
    }

    override suspend fun getProfile(): Profile.Authorized? {
        val token = dataStoreRepo.getToken() ?: return null

        return dataStoreRepo.getUserAndCityUuid().let { userCityUuid ->
            val profile = getCacheOrData(
                isCacheValid = {
                    cachedUserUuid == userCityUuid.userUuid
                },
                onLocalRequest = {
                    getProfileLocally(
                        userUuid = userCityUuid.userUuid,
                        cityUuid = userCityUuid.cityUuid
                    )
                },
                onApiRequest = {
                    networkConnector.getProfile(token = token)
                },
                onSaveLocally = ::saveProfileLocally,
                serverToDomainModel = profileMapper::toProfile
            )
            cachedUserUuid = profile?.userUuid
            profile
        }
    }

    override suspend fun clearUserCache() {
        dataStoreRepo.clearUserData()
    }

    override suspend fun getToken(): String? {
        return dataStoreRepo.getToken()
    }

    override suspend fun disableUser(token: String) {
        networkConnector.patchSettings(token, PatchUserServer(isActive = false))
    }

    private suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertUser(profileMapper.toUserEntity(profile))
        }
    }

    private suspend fun getProfileLocally(userUuid: String, cityUuid: String): Profile.Authorized? {
        return userDao.getUserByUuid(userUuid)?.let { userEntity ->
            val userAddressCount =
                userAddressDao.getUserAddressCountByUserAndCityUuid(userUuid, cityUuid)
            val lastOrderEntity = orderDao.getOrderListByUserUuid(
                userUuid = userUuid,
                count = 1
            ).firstOrNull()
            profileMapper.toProfile(
                userUuid = userEntity.uuid,
                userAddressCount = userAddressCount,
                lastOrderEntity = lastOrderEntity
            )
        }
    }
}
