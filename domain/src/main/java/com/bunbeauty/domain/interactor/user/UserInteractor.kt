package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.worker.IUserWorkerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInteractor @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val userWorkerUtil: IUserWorkerUtil,
    private val dataStoreRepo: DataStoreRepo,
    private val authUtil: IAuthUtil,
) : IUserInteractor {

    override suspend fun login() {
        val userUuid = authUtil.userUuid
        val userPhone = authUtil.userPhone
        if (userUuid != null && userPhone != null) {
            val token = userRepo.login(userUuid, userPhone)
            if (token != null) {
                dataStoreRepo.saveToken(token)
                userWorkerUtil.refreshUserBlocking(token)
            }
        }
    }

    override suspend fun logout() {
        userWorkerUtil.cancelRefreshUser()
        authUtil.signOut()
        dataStoreRepo.clearToken()
        dataStoreRepo.clearUserUuid()
    }

    override fun observeUser(): Flow<User?> {
        return authUtil.observeUserUuid().flatMapLatest { userUuid ->
            userRepo.observeProfileByUuid(userUuid ?: "").map { profile ->
                profile?.user
            }
        }
    }

    override fun observeProfile(): Flow<Profile?> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.observeProfileByUuid(userUuid ?: "")
        }
    }

    override suspend fun updateUserEmail(email: String): User? {
        if (authUtil.isAuthorize) {
            val profile = userRepo.getUserByUuid(authUtil.userUuid ?: "")
            if (profile != null && profile.user.email != email) {
                val user = profile.user.copy(email = email)
                return userRepo.updateUserEmail(user)
            }
        }

        return null
    }
}