package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {

    suspend fun getCityList(): List<City>
    fun observeCityList(): Flow<List<City>>
    fun observeCityByUuid(cityUuid: String): Flow<City?>
}