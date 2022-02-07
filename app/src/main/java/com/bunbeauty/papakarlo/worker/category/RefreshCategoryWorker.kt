package com.bunbeauty.papakarlo.worker.category

import android.content.Context
import androidx.work.WorkerParameters
import com.bunbeauty.domain.repo.CategoryRepo
import com.bunbeauty.papakarlo.worker.BaseWorker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class RefreshCategoryWorker(appContext: Context, workerParams: WorkerParameters) :
    BaseWorker(appContext, workerParams), KoinComponent {

    val categoryRepo: CategoryRepo by inject()

    override suspend fun onWork(): Result {
        categoryRepo.refreshCategoryList()
        return Result.success()
    }
}