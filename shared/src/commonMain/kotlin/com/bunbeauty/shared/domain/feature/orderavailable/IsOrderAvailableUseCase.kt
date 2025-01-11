package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo

class IsOrderAvailableUseCase(
    private val workInfoRepository: WorkInfoRepo
) {
    suspend operator fun invoke(): Boolean {
        return workInfoRepository.getWorkInfo()?.workInfoType != WorkInfo.WorkInfoType.CLOSED
    }
}
