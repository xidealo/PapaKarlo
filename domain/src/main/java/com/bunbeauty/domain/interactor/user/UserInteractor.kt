package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.worker.IUserWorkerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInteractor @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val userWorkerUtil: IUserWorkerUtil,
    private val authUtil: IAuthUtil,
): IUserInteractor {

    override suspend fun refreshUser() {
        val userUuid = authUtil.userUuid
        val userPhone = authUtil.userPhone
        if (authUtil.isAuthorize && userUuid != null && userPhone != null) {
            userWorkerUtil.refreshUserBlocking(userUuid, userPhone)
        }
    }

    override fun logout() {
        userWorkerUtil.cancelRefreshUser()
        authUtil.signOut()
    }

    override fun observeUser(): Flow<User?> {
        return authUtil.observeUserUuid().flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "").map { profile ->
                profile?.user
            }
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