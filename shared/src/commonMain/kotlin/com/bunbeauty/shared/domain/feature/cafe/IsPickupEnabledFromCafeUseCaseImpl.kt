package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

interface IsPickupEnabledFromCafeUseCase {
    suspend operator fun invoke(cafeUuid: String): Boolean
}

class IsPickupEnabledFromCafeUseCaseImpl(
    private val cafeRepo: CafeRepo,
) : IsPickupEnabledFromCafeUseCase {
    override suspend operator fun invoke(cafeUuid: String): Boolean {
        val workType = cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workType ?: Cafe.WorkType.CLOSED
        val canBePickup =
            workType == Cafe.WorkType.PICKUP || workType == Cafe.WorkType.DELIVERY_AND_PICKUP
        return canBePickup
    }
}
