package com.bunbeauty.papakarlo.worker.menu_product

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshMenuProductWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Inject
    lateinit var menuProductRepo: MenuProductRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        menuProductRepo.refreshMenuProductList()
        return Result.success()
    }
}