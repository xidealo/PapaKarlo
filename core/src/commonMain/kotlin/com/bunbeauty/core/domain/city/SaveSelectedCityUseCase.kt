package com.bunbeauty.core.domain.city

import com.bunbeauty.core.domain.repo.CityRepo

class SaveSelectedCityUseCase(
    val cityRepo: CityRepo,
) {
    suspend operator fun invoke(cityUuid: String) = cityRepo.saveSelectedCityUuid(cityUuid)
}
