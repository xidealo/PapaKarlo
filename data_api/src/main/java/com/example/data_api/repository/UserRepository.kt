package com.example.data_api.repository

import com.bunbeauty.common.Constants.NOT_FOUND_WITH_UUID
import com.bunbeauty.common.Logger.USER_TAG
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.example.data_api.dao.UserDao
import com.example.data_api.handleNullableResult
import com.example.data_api.mapFlow
import com.example.domain_api.mapper.IProfileMapper
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.post.ProfilePostServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val authUtil: IAuthUtil,
    private val profileMapper: IProfileMapper,
    private val userDao: UserDao
) : UserRepo {

    override suspend fun refreshUser() {
        val userUuid = authUtil.userUuid
        val userPhone = authUtil.userPhone
        if (authUtil.isAuthorize && userPhone != null && userUuid != null) {
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
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getUserByUuid(userUuid)?.let { user ->
            profileMapper.toModel(user)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(profileMapper::toModel)
    }

    override suspend fun updateUserEmail(profile: Profile) {
        val userEmailServer = profileMapper.toUserEmailServer(profile)
        apiRepo.patchProfileEmail(profile.uuid, userEmailServer)
            .handleNullableResult(USER_TAG) { patchedUser ->
                patchedUser?.let {
                    userDao.update(profileMapper.toEntityModel(patchedUser).user)
                }
            }
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            userDao.insertProfile(profileMapper.toEntityModel(profile))
        }
    }
}