package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class IsOrderAvailableUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(): Boolean {
        val cafe = cafeRepo.getCafeByUuid(cafeUuid)

        return cafe?.workType != Cafe.WorkType.CLOSED
    }
}
