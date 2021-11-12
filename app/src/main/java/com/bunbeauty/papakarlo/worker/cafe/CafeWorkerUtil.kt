package com.bunbeauty.papakarlo.worker.cafe

import androidx.work.workDataOf
import com.bunbeauty.common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.domain.worker.ICafeWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class CafeWorkerUtil @Inject constructor() : BaseWorkerUtil(), ICafeWorkerUtil {

    override fun refreshCafeList(selectedCafeUUid: String) {
        val data = workDataOf(
            SELECTED_CITY_UUID to selectedCafeUUid
        )
        RefreshCafeWorker::class.java.startWithReplace(data)
    }
}
