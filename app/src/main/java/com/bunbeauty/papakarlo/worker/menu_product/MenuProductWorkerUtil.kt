package com.bunbeauty.papakarlo.worker.menu_product

import com.bunbeauty.domain.worker.IMenuProductWorkerUtil
import com.bunbeauty.papakarlo.worker.BaseWorkerUtil
import javax.inject.Inject

class MenuProductWorkerUtil @Inject constructor(): BaseWorkerUtil(), IMenuProductWorkerUtil {

    override fun refreshMenuProductList() {
        RefreshMenuProductWorker::class.start()
    }
}