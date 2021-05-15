package com.example.data_api.repository

import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.domain.auth.AuthUtil
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.UserRepo
import com.example.data_api.dao.UserAddressDao
import com.example.data_api.dao.UserDao
import com.example.data_api.handleResult
import com.example.data_api.mapFlow
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.server.ProfileServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val authUtil: AuthUtil,
    private val userMapper: IUserMapper,
    private val userDao: UserDao,
    private val userAddressDao: UserAddressDao
) : UserRepo {

    override suspend fun refreshUser() {
        val userUuid = authUtil.userUuid
        val userPhone = authUtil.userPhone
        if (authUtil.isAuthorize && userPhone != null && userUuid != null) {
            apiRepo.getUserByUuid(userUuid).handleResult(USER_TAG) { user ->
                if (user == null) {
                    val newUser = ProfileServer(
                        uuid = userUuid,
                        phone = userPhone,
                        email = null,
                        addressList = emptyList(),
                        orderList = emptyList(),
                    )
                    apiRepo.postUser(newUser).handleResult(USER_TAG) { postedUser ->
                        saveUser(postedUser)
                    }
                } else {
                    saveUser(user)
                }
            }
        }
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getUserByUuid(userUuid)?.let { user ->
            userMapper.toModel(user)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toModel)
    }

    override suspend fun updateUserEmail(profile: Profile) {
        val userEmailServer = userMapper.toUserEmailServer(profile)
        apiRepo.patchUserEmail(profile.uuid, userEmailServer)
            .handleResult(USER_TAG) { patchedUser ->
                patchedUser?.let {
                    userDao.update(userMapper.toEntityModel(patchedUser).user)
                }
            }
    }

    suspend fun saveUser(profile: ProfileServer?) {
        if (profile != null) {
            val userWithAddresses = userMapper.toEntityModel(profile)
            userDao.insert(userWithAddresses.user)
            userAddressDao.insertAll(userWithAddresses.userAddressList)
        }
    }
}