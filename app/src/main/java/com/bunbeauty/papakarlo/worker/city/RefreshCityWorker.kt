package com.bunbeauty.papakarlo.worker.city

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshCityWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Api
    @Inject
    lateinit var cityRepo: CityRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        cityRepo.refreshCityList()
        return Result.success()
    }

}