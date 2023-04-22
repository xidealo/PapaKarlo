package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo

class GetSelectedCityUseCase(
    private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): City? {
        return dataStoreRepo.getSelectedCityUuid()?.let { cityUuid ->
            cityRepo.getCityByUuid(cityUuid)
        }
    }
}