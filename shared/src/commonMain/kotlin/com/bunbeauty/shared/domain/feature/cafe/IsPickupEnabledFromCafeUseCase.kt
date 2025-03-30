package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe.WorkType
import com.bunbeauty.shared.domain.repo.CafeRepo

//TODO add tests
class IsPickupEnabledFromCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Boolean {
        val workType = cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workType ?: WorkType.CLOSED
        val canBePickup =
            workType == WorkType.PICKUP || workType == WorkType.DELIVERY_AND_PICKUP
        return canBePickup
    }
}