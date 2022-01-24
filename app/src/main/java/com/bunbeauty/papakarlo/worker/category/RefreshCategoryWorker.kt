package com.bunbeauty.papakarlo.worker.category

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CategoryRepo
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.worker.BaseWorker
import javax.inject.Inject

class RefreshCategoryWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams) {

    @Api
    @Inject
    lateinit var categoryRepo: CategoryRepo

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override suspend fun onWork(): Result {
        categoryRepo.refreshCategoryList()
        return Result.success()
    }
}