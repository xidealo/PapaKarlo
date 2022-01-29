package com.bunbeauty.papakarlo.worker.street

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshStreetWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Inject
    lateinit var streetRepo: StreetRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        val selectedCityUuid = inputData.getString(SELECTED_CITY_UUID)
        logD(tag, "selectedCityUuid = $selectedCityUuid")
        if (selectedCityUuid == null) {
            return Result.failure()
        }

        streetRepo.refreshStreetList(selectedCityUuid)
        return Result.success()
    }
}