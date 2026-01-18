package com.bunbeauty.core.domain.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.domain.repo.CityRepo

class GetCityListUseCase(
    private val cityRepo: CityRepo,
) {
    suspend operator fun invoke(): List<City> = cityRepo.getCityList()
}
