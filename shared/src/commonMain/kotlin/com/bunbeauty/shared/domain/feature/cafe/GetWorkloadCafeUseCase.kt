package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetWorkloadCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Cafe.Workload =
        cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workload ?: Cafe.Workload.LOW
}
