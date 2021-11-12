package com.bunbeauty.papakarlo.worker.user

import androidx.work.workDataOf
import com.bunbeauty.common.Constants.USER_PHONE
import com.bunbeauty.common.Constants.USER_UUID
import com.bunbeauty.domain.worker.IUserWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class UserWorkerUtil @Inject constructor() : BaseWorkerUtil(), IUserWorkerUtil {

    override fun refreshUser(userUuid: String, userPhone: String) {
        val data = workDataOf(
            USER_UUID to userUuid,
            USER_PHONE to userPhone
        )
        RefreshUserWorker::class.java.startWithReplace(data)
    }

    override suspend fun refreshUserBlocking(userUuid: String, userPhone: String) {
        val data = workDataOf(
            USER_UUID to userUuid,
            USER_PHONE to userPhone
        )
        RefreshUserWorker::class.java.startWithReplaceBlocking(data)
    }
}