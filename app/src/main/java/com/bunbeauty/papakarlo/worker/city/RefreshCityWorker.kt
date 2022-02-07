package com.bunbeauty.papakarlo.worker.city

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshCityWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val cityRepo: CityRepo by inject()

    override suspend fun onWork(): Result {
        cityRepo.refreshCityList()
        return Result.success()
    }

}