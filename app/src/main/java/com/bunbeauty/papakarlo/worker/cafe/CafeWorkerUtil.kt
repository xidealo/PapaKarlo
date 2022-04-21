package com.bunbeauty.papakarlo.worker.cafe

import androidx.work.WorkManager
import androidx.work.workDataOf
import core_common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.domain.worker.ICafeWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class CafeWorkerUtil(workManager: WorkManager) : BaseWorkerUtil(workManager), ICafeWorkerUtil {

    override fun refreshCafeList(selectedCafeUUid: String) {
        val data = workDataOf(
            SELECTED_CITY_UUID to selectedCafeUUid
        )
        RefreshCafeWorker::class.startWithReplace(data)
    }
}
