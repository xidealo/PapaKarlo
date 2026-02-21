package com.bunbeauty.core.domain.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.domain.repo.CityRepo

interface GetSelectedCityUseCase {
    suspend operator fun invoke(): City?
}

class GetSelectedCityUseCaseImpl(
    private val cityRepo: CityRepo,
) : GetSelectedCityUseCase {
    override suspend operator fun invoke(): City? {
        return cityRepo.getSelectedCityUuid()?.let {
            cityRepo.getCityByUuid(cityUuid = it)
        }
    }
}
