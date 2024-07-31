package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.DataStoreRepo

class SaveSelectedCityUseCase(
    private val dataStoreRepo: DataStoreRepo
) {

    suspend operator fun invoke(cityUuid: String) {
        return dataStoreRepo.saveSelectedCityUuid(cityUuid)
    }
}
