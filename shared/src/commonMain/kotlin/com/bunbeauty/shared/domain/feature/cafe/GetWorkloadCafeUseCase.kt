package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.Cafe.WorkType
import com.bunbeauty.shared.domain.repo.CafeRepo

//TODO add tests
class GetWorkloadCafeUseCase(
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(cafeUuid: String): Cafe.Workload {
        return cafeRepo.getCafeByUuid(cafeUuid = cafeUuid)?.workload ?: Cafe.Workload.LOW
    }
}