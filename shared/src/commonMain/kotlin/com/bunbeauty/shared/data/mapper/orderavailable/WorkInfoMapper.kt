package com.bunbeauty.shared.data.mapper.orderavailable

import com.bunbeauty.shared.data.network.model.WorkInfoServer
import com.bunbeauty.shared.domain.model.order.WorkInfo

val mapWorkInfoServerToWorkInfo: WorkInfoServer.() -> WorkInfo =
    {
        WorkInfo(
            workInfoType = WorkInfo.WorkInfoType.valueOf(workType)
        )
    }
