package com.bunbeauty.core.domain.cafe

import com.bunbeauty.core.domain.exeptions.EmptyCafeListException
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.domain.repo.CafeRepo

interface GetCafeListUseCase {
    suspend operator fun invoke(): List<Cafe>
}

class GetCafeListUseCaseImpl(
    private val cafeRepo: CafeRepo,
) : GetCafeListUseCase {
    override suspend operator fun invoke(): List<Cafe> {
        return cafeRepo
            .getCafeList()
            .filter { cafe ->
                cafe.isVisible
            }.ifEmpty {
                throw EmptyCafeListException()
            }
    }
}
