package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class IsDeliveryEnabledFromCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Boolean {
        val workType = cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workType ?: Cafe.WorkType.CLOSED
        val canBeDelivery =
            workType == Cafe.WorkType.DELIVERY || workType == Cafe.WorkType.DELIVERY_AND_PICKUP
        return canBeDelivery
    }
}
