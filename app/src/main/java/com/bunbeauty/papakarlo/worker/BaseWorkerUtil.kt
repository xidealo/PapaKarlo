package com.bunbeauty.papakarlo.worker

import androidx.lifecycle.asFlow
import androidx.work.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transformWhile
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseWorkerUtil {

    @Inject
    lateinit var workManager: WorkManager

    protected fun KClass<out ListenableWorker>.start(workDataOf: Data = workDataOf()) {
        workManager.enqueueUniqueWork(
            java.simpleName,
            ExistingWorkPolicy.KEEP,
            getOneTimeWork(workDataOf)
        )
    }

    protected fun KClass<out ListenableWorker>.startWithReplace(workDataOf: Data = workDataOf()) {
        workManager.enqueueUniqueWork(
            java.simpleName,
            ExistingWorkPolicy.REPLACE,
            getOneTimeWork(workDataOf)
        )
    }

    protected suspend fun KClass<out ListenableWorker>.startWithReplaceBlocking(workDataOf: Data = workDataOf()) {
        val workRequest = getOneTimeWork(workDataOf)
        workManager.enqueueUniqueWork(java.simpleName, ExistingWorkPolicy.REPLACE, workRequest)
        workManager.observe(workRequest).transformWhile<WorkInfo, Unit> { workInfo ->
            !workInfo.isFinished()
        }.collect()
    }

    protected fun <T : ListenableWorker> KClass<T>.cancel() {
        workManager.cancelUniqueWork(java.simpleName)
    }

    private fun KClass<out ListenableWorker>.getOneTimeWork(workDataOf: Data): OneTimeWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequest.Builder(java).apply {
            setInputData(workDataOf)
            setConstraints(constraints)
            setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MILLISECONDS)
            addTag(COMMON_WORKER_TAG)
        }.build()
    }

    private fun WorkInfo.isFinished(): Boolean {
        return state == WorkInfo.State.SUCCEEDED
                || state == WorkInfo.State.FAILED
                || state == WorkInfo.State.CANCELLED
    }

    private fun WorkManager.observe(work: OneTimeWorkRequest): Flow<WorkInfo> {
        return getWorkInfoByIdLiveData(work.id).asFlow()
    }

    companion object {
        private const val COMMON_WORKER_TAG = "common worker tag"
    }
}