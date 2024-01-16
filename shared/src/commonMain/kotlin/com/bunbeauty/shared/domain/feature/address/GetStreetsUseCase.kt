package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

@Deprecated("Unused")
class GetStreetsUseCase(
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): List<Street> {
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()

        return streetRepo.getStreetList(
            userUuid = userUuid,
            cityUuid = cityUuid
        )
    }
}