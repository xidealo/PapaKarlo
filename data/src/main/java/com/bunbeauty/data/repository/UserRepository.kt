package com.bunbeauty.data.repository

import com.bunbeauty.common.Constants.COMPANY_UUID
import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.data.handleResult
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.mapper.profile.IProfileMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.model.login.LoginPostServer
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.data.sql_delight.dao.order.IOrderDao
import com.bunbeauty.data.sql_delight.dao.user.IUserDao
import com.bunbeauty.data.sql_delight.dao.user_address.IUserAddressDao
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UserRepository constructor(
    private val apiRepo: ApiRepo,
    private val profileMapper: IProfileMapper,
    private val userMapper: IUserMapper,
    private val userDao: IUserDao,
    private val userAddressDao: IUserAddressDao,
    private val orderDao: IOrderDao,
    private val dataStoreRepo: DataStoreRepo
) : UserRepo {

    override suspend fun login(userUuid: String, userPhone: String): String? {
        val loginPost = LoginPostServer(
            firebaseUuid = userUuid,
            phoneNumber = userPhone,
            companyUuid = COMPANY_UUID
        )
        return apiRepo.postLogin(loginPost).handleResultAndReturn(USER_TAG) { authResponseServer ->
            authResponseServer.token
        }
    }

    override suspend fun refreshProfile(token: String) {
        apiRepo.getProfile(token).handleResult(USER_TAG) { profile ->
            saveProfileLocally(profile)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toUser)
    }

    override fun observeProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Profile?> {
        return userDao.observeUserByUuid(userUuid).flatMapLatest { userEntity ->
            userAddressDao.observeUserAddressCountByUserAndCityUuid(userUuid, cityUuid)
                .flatMapLatest { userAddressCount ->
                    orderDao.observeLastOrderByUserUuid(userUuid).map { lastOrderEntity ->
                        userEntity?.let {
                            profileMapper.toProfile(
                                userUuid = userEntity.uuid,
                                userAddressCount = userAddressCount,
                                lastOrderEntity = lastOrderEntity
                            )
                        }
                    }
                }
        }
    }

    override suspend fun updateUserEmail(token: String, userUuid: String, email: String): User? {
        val patchUserServer = userMapper.toPatchServerModel(email)
        return apiRepo.patchProfileEmail(token, userUuid, patchUserServer)
            .handleResultAndReturn(USER_TAG) { profile ->
                userDao.updateUserEmailByUuid(userUuid, email)
                userMapper.toUser(profile)
            }
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertUser(profileMapper.toUserEntity(profile))
            userAddressDao.insertUserAddressList(profileMapper.toUserAddressEntityList(profile))
            orderDao.insertOrderWithProductList(profileMapper.toOrderWithProductEntityList(profile))
        }
    }
}