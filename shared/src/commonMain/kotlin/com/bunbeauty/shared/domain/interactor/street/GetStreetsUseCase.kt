package com.bunbeauty.shared.domain.interactor.street

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

class GetStreetsUseCase(
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): List<Street> {
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidThrow
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidThrow

        return streetRepo.getStreetList(
            userUuid = userUuid,
            cityUuid = cityUuid
        )
    }

    object NoUserUuidThrow : Throwable()
    object NoSelectedCityUuidThrow : Throwable()
}