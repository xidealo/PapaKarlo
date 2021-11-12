package com.bunbeauty.papakarlo.worker.user

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.common.Constants.USER_PHONE
import com.bunbeauty.common.Constants.USER_UUID
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshUserWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Api
    @Inject
    lateinit var userRepo: UserRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        val userUuid = inputData.getString(USER_UUID)
        val userPhone = inputData.getString(USER_PHONE)
        logD(tag, "userUuid = $userUuid")
        logD(tag, "userPhone = $userPhone")
        if (userUuid == null || userPhone == null) {
            return Result.failure()
        }

        userRepo.refreshUser(userUuid, userPhone)
        return Result.success()
    }
}