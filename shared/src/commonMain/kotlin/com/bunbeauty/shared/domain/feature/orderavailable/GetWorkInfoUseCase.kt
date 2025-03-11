package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetWorkInfoUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(): Cafe.WorkType? {
        return cafeRepo.getCafeByUuid()?.workType
    }
}
