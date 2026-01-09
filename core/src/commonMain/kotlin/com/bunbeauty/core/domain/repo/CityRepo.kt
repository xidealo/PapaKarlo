package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.city.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {
    suspend fun getCityList(): List<City>

    suspend fun getCityByUuid(cityUuid: String): City?

    fun observeSelectedCity(): Flow<City?>

    suspend fun getSelectedCityUuid(): String?
    suspend fun saveSelectedCityUuid(cityUuid: String)

    fun observeCityList(): Flow<List<City>>

    fun observeCityByUuid(cityUuid: String): Flow<City?>
}
