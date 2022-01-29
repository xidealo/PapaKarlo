package com.bunbeauty.papakarlo.worker.user

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.common.Constants.TOKEN_WORK_KEY
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshUserWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Inject
    lateinit var userRepo: UserRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        val token = inputData.getString(TOKEN_WORK_KEY)
        logD(tag, "token = $token")
        if (token == null) {
            return Result.failure()
        }

        userRepo.refreshUser(token)
        return Result.success()
    }
}