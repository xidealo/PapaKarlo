package com.bunbeauty.papakarlo.worker.delivery

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshDeliveryWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Api
    @Inject
    lateinit var deliveryRepo: DeliveryRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        deliveryRepo.refreshDelivery()
        return Result.success()
    }
}