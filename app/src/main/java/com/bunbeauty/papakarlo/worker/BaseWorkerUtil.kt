package com.bunbeauty.papakarlo.worker

import androidx.lifecycle.asFlow
import androidx.work.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseWorkerUtil {

    @Inject
    lateinit var workManager: WorkManager

    protected fun <T : ListenableWorker> Class<T>.start(workDataOf: Data = workDataOf()) {
        workManager.enqueueUniqueWork(
            simpleName,
            ExistingWorkPolicy.KEEP,
            getOneTimeWork(workDataOf)
        )
    }

    protected fun <T : ListenableWorker> Class<T>.startWithReplace(workDataOf: Data = workDataOf()) {
        workManager.enqueueUniqueWork(
            simpleName,
            ExistingWorkPolicy.REPLACE,
            getOneTimeWork(workDataOf)
        )
    }

    protected suspend fun <T : ListenableWorker> Class<T>.startWithReplaceBlocking(workDataOf: Data = workDataOf()) {
        val workRequest = getOneTimeWork(workDataOf)
        workManager.enqueueUniqueWork(simpleName, ExistingWorkPolicy.REPLACE, workRequest)
        workManager.observe(workRequest).collect { workInfo ->
            if (workInfo.isFinished()) {
                return@collect
            }
        }
    }

    private fun Class<out ListenableWorker>.getOneTimeWork(workDataOf: Data): OneTimeWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequest.Builder(this).apply {
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