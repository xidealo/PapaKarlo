package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.repo.CafeRepo

class GetAdditionalUtensilsUseCase(
    private val cafeRepo: CafeRepo
) {
    suspend operator fun invoke(cafeUuid: String): Boolean {
        return cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.additionalUtensils == true
    }
}
