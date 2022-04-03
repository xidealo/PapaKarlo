package com.bunbeauty.papakarlo.worker.user

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.common.Constants.TOKEN_WORK_KEY
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Deprecated("replaced on request on screen")
class RefreshUserWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val userRepo: UserRepo by inject()

    override suspend fun onWork(): Result {
        val token = inputData.getString(TOKEN_WORK_KEY)
        logD(tag, "token = $token")
        if (token == null) {
            return Result.failure()
        }

        //userRepo.refreshProfile(token)
        return Result.success()
    }
}