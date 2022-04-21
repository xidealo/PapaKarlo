package com.bunbeauty.papakarlo.worker.street

import androidx.work.WorkManager
import androidx.work.workDataOf
import core_common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.domain.worker.IStreetWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class StreetWorkerUtil(workManager: WorkManager) : BaseWorkerUtil(workManager), IStreetWorkerUtil {

    override fun refreshStreetList(selectedCityUuid: String) {
        val data = workDataOf(
            SELECTED_CITY_UUID to selectedCityUuid
        )
        RefreshStreetWorker::class.startWithReplace(data)
    }
}