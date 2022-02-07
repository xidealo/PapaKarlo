package com.bunbeauty.papakarlo.worker.user

import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bunbeauty.common.Constants.TOKEN_WORK_KEY
import com.bunbeauty.domain.worker.IUserWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class UserWorkerUtil  constructor(workManager: WorkManager) : BaseWorkerUtil(workManager), IUserWorkerUtil {

    override fun refreshUser(token: String) {
        val data = workDataOf(
            TOKEN_WORK_KEY to token
        )
        RefreshUserWorker::class.start(data)
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
