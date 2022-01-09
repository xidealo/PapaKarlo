package com.bunbeauty.papakarlo.worker.user

import androidx.work.workDataOf
import com.bunbeauty.common.Constants.TOKEN_WORK_KEY
import com.bunbeauty.domain.worker.IUserWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class UserWorkerUtil @Inject constructor() : BaseWorkerUtil(), IUserWorkerUtil {

    override fun refreshUser(token: String) {
        val data = workDataOf(
            TOKEN_WORK_KEY to token
        )
        RefreshUserWorker::class.startWithReplace(data)
    }

    override suspend fun refreshUserBlocking(token: String) {
        val data = workDataOf(
            TOKEN_WORK_KEY to token
        )
        RefreshUserWorker::class.startWithReplaceBlocking(data)
    }

    override fun cancelRefreshUser() {
        RefreshUserWorker::class.cancel()
    }
}