package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo

class SetClosedWorkInfoUseCase(
    private val workInfoRepository: WorkInfoRepo
) {
    operator fun invoke() {
        workInfoRepository.update(
            workInfo = WorkInfo(
                workInfoType = WorkInfo.WorkInfoType.CLOSED
            )
        )
    }
}
