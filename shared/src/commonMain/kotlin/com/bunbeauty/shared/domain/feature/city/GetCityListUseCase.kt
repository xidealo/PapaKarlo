package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo

class GetCityListUseCase(
    private val cityRepo: CityRepo
) {

    suspend operator fun invoke(): List<City> {
        return cityRepo.getCityList()
    }
}