package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {

    suspend fun refreshCityList()
    fun observeCityList(): Flow<List<City>>
}