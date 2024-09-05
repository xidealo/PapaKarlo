package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.city.City

interface CityRepo {
    suspend fun getCityList(): List<City>
    suspend fun getCityByUuid(cityUuid: String): City?
}
