package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.auth.AuthUtil
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.UserRepo
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val authUtil: AuthUtil
) : UserRepo {

    override suspend fun refreshUser() {
        val userUuid = authUtil.userUuid ?: ""
        when (val result = apiRepo.getUserByUuid(userUuid)) {
            is ApiResult.Success -> {
                val t = result
            }
            is ApiResult.Error -> {
               val t = result
            }
        }
    }

    override suspend fun getUserByUuid(userUuid: String): User? {
        //TODO("Not yet implemented")

        return Any() as User?
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        //TODO("Not yet implemented")

        return Any() as Flow<User?>
    }
}