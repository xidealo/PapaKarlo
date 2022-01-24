package com.bunbeauty.papakarlo.worker.category

import com.bunbeauty.domain.worker.ICategoryWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class CategoryWorkerUtil @Inject constructor() : BaseWorkerUtil(), ICategoryWorkerUtil {

    override suspend fun refreshCategoryList() {
        RefreshCategoryWorker::class.startWithReplaceBlocking()
    }
}