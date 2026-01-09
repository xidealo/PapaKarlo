package com.bunbeauty.core.domain.city

import com.bunbeauty.core.domain.repo.CityRepo

private const val DEFAULT_TIME_ZONE = "UTC+3"

interface GetSelectedCityTimeZoneUseCase {
    suspend operator fun invoke(): String
}

class GetSelectedCityTimeZoneUseCaseImpl(
    private val cityRepo: CityRepo,
) : GetSelectedCityTimeZoneUseCase {
    override suspend operator fun invoke(): String {
        return cityRepo
            .getSelectedCityUuid()
            ?.let { cityUuid ->
                cityRepo.getCityByUuid(cityUuid)
            }?.timeZone ?: DEFAULT_TIME_ZONE
    }

}
