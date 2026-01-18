package com.bunbeauty.core.domain.cafe

import com.bunbeauty.core.model.cafe.Cafe

class HasOpenedCafeUseCase(
    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase,
) {
    suspend operator fun invoke(cafeList: List<Cafe>): Boolean = cafeList.any { cafe -> isPickupEnabledFromCafeUseCase(cafe.uuid) }
}
