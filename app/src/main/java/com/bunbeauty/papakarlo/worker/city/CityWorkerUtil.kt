package com.bunbeauty.papakarlo.worker.city

import androidx.work.WorkManager
import com.bunbeauty.domain.worker.ICityWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class CityWorkerUtil(workManager: WorkManager) : BaseWorkerUtil(workManager), ICityWorkerUtil {

    override fun refreshCityList() {
        RefreshCityWorker::class.start()
    }
}