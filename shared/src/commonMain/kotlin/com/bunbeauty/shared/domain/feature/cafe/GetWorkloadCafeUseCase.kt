package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.domain.repo.CafeRepo

class GetWorkloadCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Cafe.Workload =
        cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workload ?: Cafe.Workload.LOW
}
