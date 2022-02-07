package com.bunbeauty.papakarlo.worker.cafe

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.common.Constants.SELECTED_CITY_UUID
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshCafeWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val cafeRepo: CafeRepo by inject()

    override suspend fun onWork(): Result {
        val selectedCityUuid = inputData.getString(SELECTED_CITY_UUID)
        logD(tag, "selectedCityUuid = $selectedCityUuid")
        if (selectedCityUuid == null) {
            return Result.failure()
        }

        cafeRepo.refreshCafeList(selectedCityUuid)
        return Result.success()
    }
}