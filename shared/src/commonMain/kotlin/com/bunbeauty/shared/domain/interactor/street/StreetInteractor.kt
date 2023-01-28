package com.bunbeauty.shared.domain.interactor.street

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

class StreetInteractor(
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo
) : IStreetInteractor {

    override suspend fun getStreetList(): List<Street>? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null

        return streetRepo.getStreetList(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).ifEmpty { null }
    }
}