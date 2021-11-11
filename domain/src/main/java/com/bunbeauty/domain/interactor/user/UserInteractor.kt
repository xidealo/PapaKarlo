package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.worker.IUserWorkerUtil
import javax.inject.Inject

class UserInteractor @Inject constructor(
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
}