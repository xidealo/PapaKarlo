package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetCafeListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo,
) {

    suspend operator fun invoke(): List<Cafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        return cafeRepo.getCafeList(cityUuid)
    }
}