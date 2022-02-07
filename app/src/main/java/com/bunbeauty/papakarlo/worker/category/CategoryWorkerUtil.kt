package com.bunbeauty.papakarlo.worker.category

import androidx.work.WorkManager
import com.bunbeauty.domain.worker.ICategoryWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class CategoryWorkerUtil(workManager: WorkManager)  : BaseWorkerUtil(workManager), ICategoryWorkerUtil {

    override suspend fun refreshCategoryList() {
        RefreshCategoryWorker::class.startWithReplaceBlocking()
    }
}