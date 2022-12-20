package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.CityRepo

private const val DEFAULT_TIME_ZONE = "UTC+3"

class GetSelectedCityTimeZoneUseCase(
    private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): String {
        return dataStoreRepo.getSelectedCityUuid()?.let { cityUuid ->
            cityRepo.getCityByUuid(cityUuid)
        }?.timeZone ?: DEFAULT_TIME_ZONE
    }
}