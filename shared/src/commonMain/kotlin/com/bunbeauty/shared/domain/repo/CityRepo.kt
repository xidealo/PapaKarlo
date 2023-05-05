package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.city.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {

    suspend fun getCityList(): List<City>
    suspend fun getCityByUuid(cityUuid: String): City?
    fun observeCityList(): Flow<List<City>>
    fun observeCityByUuid(cityUuid: String): Flow<City?>
}