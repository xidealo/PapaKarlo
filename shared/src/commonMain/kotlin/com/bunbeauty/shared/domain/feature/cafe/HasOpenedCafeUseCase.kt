package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe

class HasOpenedCafeUseCase(
    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase
) {
    suspend operator fun invoke(cafeList: List<Cafe>): Boolean {
        return cafeList.any { cafe -> isPickupEnabledFromCafeUseCase(cafe.uuid) }
    }
}
