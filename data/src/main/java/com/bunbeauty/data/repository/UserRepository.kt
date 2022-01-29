package com.bunbeauty.data.repository

import com.bunbeauty.common.Constants.COMPANY_UUID
import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.data.database.dao.UserDao
import com.bunbeauty.data.handleResult
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.mapper.profile.IProfileMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.model.login.LoginPostServer
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
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

    override fun observeProfileByUuid(userUuid: String): Flow<LightProfile?> {
        return userDao.observeProfileByUuid(userUuid).mapFlow(profileMapper::toLightProfile)
    }

    override suspend fun updateUserEmail(token: String, userUuid: String, email: String): User? {
        val patchUserServer = userMapper.toPatchServerModel(email)
        return apiRepo.patchProfileEmail(token, userUuid, patchUserServer)
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