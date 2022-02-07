package com.bunbeauty.papakarlo.worker.delivery

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshDeliveryWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val deliveryRepo: DeliveryRepo by inject()

    override suspend fun onWork(): Result {
        deliveryRepo.refreshDelivery()
        return Result.success()
    }
}