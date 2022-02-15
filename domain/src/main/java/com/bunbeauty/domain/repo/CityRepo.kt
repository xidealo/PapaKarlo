package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {

    suspend fun getCityList(): List<City>
    fun observeCityList(): Flow<List<City>>
    fun observeCityByUuid(cityUuid: String): Flow<City?>
}