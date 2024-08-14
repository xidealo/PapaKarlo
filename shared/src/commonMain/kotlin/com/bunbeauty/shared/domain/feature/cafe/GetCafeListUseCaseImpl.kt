package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

interface GetCafeListUseCase {
    suspend operator fun invoke(): List<Cafe>
}

class GetCafeListUseCaseImpl(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo
) : GetCafeListUseCase {
    override suspend operator fun invoke(): List<Cafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        return cafeRepo.getCafeList(cityUuid)
            .filter { cafe ->
                cafe.isVisible
            }.ifEmpty {
                throw EmptyCafeListException()
            }
    }
}
