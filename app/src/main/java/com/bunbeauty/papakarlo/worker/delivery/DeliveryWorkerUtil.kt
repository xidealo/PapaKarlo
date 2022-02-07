package com.bunbeauty.papakarlo.worker.delivery

import androidx.work.WorkManager
import com.bunbeauty.domain.worker.IDeliveryWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class DeliveryWorkerUtil  constructor(workManager: WorkManager): BaseWorkerUtil(workManager), IDeliveryWorkerUtil {

    override fun refreshDelivery() {
        RefreshDeliveryWorker::class.start()
    }
}