package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe.WorkType
import com.bunbeauty.shared.domain.repo.CafeRepo

//TODO add tests
class IsDeliveryEnabledFromCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Boolean {
        val workType = cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workType ?: WorkType.CLOSED
        val canBeDelivery =
            workType == WorkType.DELIVERY || workType == WorkType.DELIVERY_AND_PICKUP
        return canBeDelivery
    }
}