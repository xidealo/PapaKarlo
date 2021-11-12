package com.bunbeauty.papakarlo.worker.delivery

import com.bunbeauty.domain.worker.IDeliveryWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class DeliveryWorkerUtil @Inject constructor(): BaseWorkerUtil(), IDeliveryWorkerUtil {

    override fun refreshDelivery() {
        RefreshDeliveryWorker::class.java.start()
    }
}