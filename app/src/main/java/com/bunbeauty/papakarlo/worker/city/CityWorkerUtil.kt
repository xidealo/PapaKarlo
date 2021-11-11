package com.bunbeauty.papakarlo.worker.city

import com.bunbeauty.domain.worker.ICityWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class CityWorkerUtil @Inject constructor(): BaseWorkerUtil(), ICityWorkerUtil {

    override fun refreshCityList() {
        RefreshCityWorker::class.start()
    }
}