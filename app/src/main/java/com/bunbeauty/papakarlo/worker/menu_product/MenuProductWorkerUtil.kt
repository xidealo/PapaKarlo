package com.bunbeauty.papakarlo.worker.menu_product

import androidx.work.WorkManager
import com.bunbeauty.domain.worker.IMenuProductWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil

class MenuProductWorkerUtil constructor(workManager: WorkManager) : BaseWorkerUtil(workManager),
    IMenuProductWorkerUtil {
    override fun refreshMenuProductList() {
        RefreshMenuProductWorker::class.start()
    }
}