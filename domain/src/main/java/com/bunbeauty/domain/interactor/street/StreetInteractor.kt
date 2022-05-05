package com.bunbeauty.domain.interactor.street

import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo

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