package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.repo.CafeRepo

class SetClosedWorkInfoUseCase(
    private val cafeRepo: CafeRepo
) {
    operator fun invoke() {
        TODO("CLOSE CAFE")
//        cafeRepo.update(
//            workInfo = WorkInfo(
//                workInfoType = WorkInfo.WorkInfoType.CLOSED
//            )
//        )
    }
}
