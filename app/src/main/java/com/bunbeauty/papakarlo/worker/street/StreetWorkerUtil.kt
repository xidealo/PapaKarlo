package com.bunbeauty.papakarlo.worker.street

import androidx.work.workDataOf
import com.bunbeauty.common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.domain.worker.IStreetWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class StreetWorkerUtil @Inject constructor() : BaseWorkerUtil(), IStreetWorkerUtil {

    override fun refreshStreetList(selectedCityUuid: String) {
        val data = workDataOf(
            SELECTED_CITY_UUID to selectedCityUuid
        )
        RefreshStreetWorker::class.java.startWithReplace(data)
    }
}