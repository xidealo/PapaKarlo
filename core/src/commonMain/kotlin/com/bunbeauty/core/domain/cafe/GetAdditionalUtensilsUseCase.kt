package com.bunbeauty.core.domain.cafe

import com.bunbeauty.core.domain.repo.CafeRepo

class GetAdditionalUtensilsUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Boolean = cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.additionalUtensils == true
}
