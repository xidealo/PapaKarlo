package com.bunbeauty.papakarlo.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.papakarlo.PapaKarloApplication

abstract class BaseWorker(private val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    protected val tag: String =
        javaClass.simpleName.substring(javaClass.simpleName.lastIndexOf('.') + 1)

    override suspend fun doWork(): Result {
        return try {
            logD(tag, "Start")
            val result = onWork()
            logD(tag, "Finish $result")

            result
        } catch (exception: Exception) {
            logD(tag, "Exception " + exception.message)
            Result.failure()
        }
    }

    abstract suspend fun onWork(): Result
}