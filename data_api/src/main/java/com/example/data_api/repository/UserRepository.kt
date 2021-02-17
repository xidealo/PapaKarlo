package com.example.data_api.repository

import com.bunbeauty.common.Constants.COMPANY_UUID
import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.example.data_api.dao.UserDao
import com.example.data_api.handleResult
import com.example.data_api.handleResultAndReturn
import com.example.domain_api.mapper.IProfileMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.server.login.LoginPostServer
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val profileMapper: IProfileMapper,
    private val userMapper: IUserMapper,
    private val userDao: UserDao,
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

    override suspend fun refreshUser(token: String) {
        apiRepo.getProfile(token).handleResult(USER_TAG) { profile ->
            saveProfileLocally(profile)
        }
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getUserByUuid(userUuid)?.let { user ->
            profileMapper.toModel(user)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toModel)
    }

    override fun observeProfileByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeProfileByUuid(userUuid).mapFlow(profileMapper::toModel)
    }

    override suspend fun updateUserEmail(userUuid: String, email: String): User? {
        val patchUserServer = userMapper.toPatchServerModel(userUuid)
        return apiRepo.patchProfileEmail(userUuid, patchUserServer)
            .handleResultAndReturn(USER_TAG) { profile ->
                userDao.update(userMapper.toUserUpdate(profile))
                userMapper.toModel(profile)
            }
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertProfile(profileMapper.toEntityModel(profile))
        }
    }
}