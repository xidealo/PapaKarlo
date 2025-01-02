package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.orderavailable.mapWorkInfoServerToWorkInfo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo
import com.bunbeauty.shared.extension.getNullableResult

class WorkInfoRepository(
    private val networkConnector: NetworkConnector
) : WorkInfoRepo {

    private var cache: WorkInfo? = null

    override suspend fun fetchWorkInfo(): WorkInfo? {
        return networkConnector.getWorkInfo()
            .getNullableResult { orderAvailableServer ->
                val orderAvailable =
                    orderAvailableServer.mapWorkInfoServerToWorkInfo()
                cache = orderAvailable
                orderAvailable
            }
    }

    override suspend fun getWorkInfo(): WorkInfo? {
        return cache ?: fetchWorkInfo()
    }

    override fun update(workInfo: WorkInfo) {
        cache = workInfo
    }
}
