package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.data.dao.order.IOrderDao
import com.bunbeauty.data.dao.user.IUserDao
import com.bunbeauty.data.dao.user_address.IUserAddressDao
import com.bunbeauty.data.mapper.profile.IProfileMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.model.login.LoginPostServer
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import core_common.Constants.COMPANY_UUID
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val apiRepo: ApiRepo,
    private val profileMapper: IProfileMapper,
    private val userMapper: IUserMapper,
    private val userDao: IUserDao,
    private val userAddressDao: IUserAddressDao,
    private val orderDao: IOrderDao,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Profile.Authorized>(), UserRepo {

    override val tag: String = USER_TAG

    override suspend fun login(userUuid: String, userPhone: String): String? {
        val loginPost = LoginPostServer(
            firebaseUuid = userUuid,
            phoneNumber = userPhone,
            companyUuid = COMPANY_UUID
        )
        return apiRepo.postLogin(loginPost).getNullableResult { authResponseServer ->
            authResponseServer.token
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toUser)
    }

    override suspend fun getProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String
    ): Profile.Authorized? {
        return getCacheOrData(
            isCacheValid = { cache ->
                cache.userUuid == userUuid
            },
            onApiRequest = {
                apiRepo.getProfile(token)
            },
            onLocalRequest = {
                getProfileLocally(userUuid, cityUuid)
            },
            onSaveLocally = ::saveProfileLocally,
            serverToDomainModel = profileMapper::toProfile
        )
    }

    override suspend fun updateUserEmail(token: String, userUuid: String, email: String): User? {
        val patchUserServer = userMapper.toPatchServerModel(email)
        return apiRepo.patchProfileEmail(token, userUuid, patchUserServer)
            .getNullableResult { profile ->
                userDao.updateUserEmailByUuid(userUuid, email)
                userMapper.toUser(profile)
            }
    }

    override suspend fun clearUserCache() {
        dataStoreRepo.clearToken()
        dataStoreRepo.clearUserUuid()
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertUser(profileMapper.toUserEntity(profile))
            userAddressDao.insertUserAddressList(profileMapper.toUserAddressEntityList(profile))
            orderDao.insertOrderWithProductList(profileMapper.toOrderWithProductEntityList(profile))
        }
    }

    suspend fun getProfileLocally(userUuid: String, cityUuid: String): Profile.Authorized? {
        return userDao.getUserByUuid(userUuid)?.let { userEntity ->
            val userAddressCount =
                userAddressDao.getUserAddressCountByUserAndCityUuid(userUuid, cityUuid)
            val lastOrderEntity = orderDao.getLastOrderByUserUuid(userUuid)
            profileMapper.toProfile(
                userUuid = userEntity.uuid,
                userAddressCount = userAddressCount,
                lastOrderEntity = lastOrderEntity
            )
        }
    }

}