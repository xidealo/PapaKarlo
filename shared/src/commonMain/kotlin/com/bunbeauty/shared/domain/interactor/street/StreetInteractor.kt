package com.bunbeauty.shared.domain.interactor.street

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

class StreetInteractor(
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo
) : IStreetInteractor {

    override suspend fun getStreetList(): List<Street>? {
        return dataStoreRepo.getSelectedCityUuid()?.let { selectedCityUuid ->
            streetRepo.getStreetList(selectedCityUuid).ifEmpty { null }
        }
    }
}