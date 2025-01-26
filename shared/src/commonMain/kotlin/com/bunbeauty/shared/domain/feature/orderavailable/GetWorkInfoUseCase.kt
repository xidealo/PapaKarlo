package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo

class GetWorkInfoUseCase(
    private val workInfoRepository: WorkInfoRepo,
) {
    suspend operator fun invoke(): WorkInfo? {
        return workInfoRepository.getWorkInfo()
    }
}
