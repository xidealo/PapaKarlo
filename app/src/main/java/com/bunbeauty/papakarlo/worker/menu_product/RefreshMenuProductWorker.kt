package com.bunbeauty.papakarlo.worker.menu_product

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshMenuProductWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val menuProductRepo: MenuProductRepo by inject()

    override suspend fun onWork(): Result {
        menuProductRepo.refreshMenuProductList()
        return Result.success()
    }
}