package com.example.data_api.repository

import com.bunbeauty.common.Constants.NOT_FOUND_WITH_UUID
import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.UserRepo
import com.example.data_api.dao.UserDao
import com.example.data_api.handleNullableResult
import com.example.data_api.handleResultAndReturn
import com.example.data_api.mapFlow
import com.example.domain_api.mapper.IProfileMapper
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.post.ProfilePostServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val profileMapper: IProfileMapper,
    private val userDao: UserDao
) : UserRepo {

    override suspend fun refreshUser(userUuid: String, userPhone: String) {
        apiRepo.getProfileByUuid(userUuid).handleNullableResult(USER_TAG, { apiError ->
            if (apiError.code == NOT_FOUND_WITH_UUID) {
                val newUser = ProfilePostServer(
                    uuid = userUuid,
                    phone = userPhone,
                    email = "",
                )
                apiRepo.postProfile(newUser).handleNullableResult(USER_TAG) { postedProfile ->
                    saveProfileLocally(postedProfile)
                }
            }
        }) { profile ->
            saveProfileLocally(profile)
        }
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getUserByUuid(userUuid)?.let { user ->
            profileMapper.toModel(user)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(profileMapper::toModel)
    }

    override suspend fun updateUserEmail(user: User): User? {
        val userEmailServer = profileMapper.toUserEmailServer(user)
        return apiRepo.patchProfileEmail(user.uuid, userEmailServer)
            .handleResultAndReturn(USER_TAG) { patchedUser ->
                userDao.update(profileMapper.toEntityModel(patchedUser).user)
                profileMapper.toModel(patchedUser).user
            }
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            userDao.insertProfile(profileMapper.toEntityModel(profile))
        }
    }
}