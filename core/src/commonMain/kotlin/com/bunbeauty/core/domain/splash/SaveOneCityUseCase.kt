package com.bunbeauty.core.domain.splash

import com.bunbeauty.core.domain.exeptions.NoCityException
import com.bunbeauty.core.domain.repo.CityRepo

class SaveOneCityUseCase(
    private val cityRepo: CityRepo,
) {
    suspend operator fun invoke() {
        val city = cityRepo.getCityList().firstOrNull() ?: throw NoCityException()
        cityRepo.saveSelectedCityUuid(cityUuid = city.uuid)
    }
}
