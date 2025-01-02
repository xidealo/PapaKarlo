package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.order.WorkInfo

interface WorkInfoRepo {
    suspend fun fetchWorkInfo(): WorkInfo?
    suspend fun getWorkInfo(): WorkInfo?
    fun update(workInfo: WorkInfo)
}
