package com.bunbeauty.data.sql_delight.dao.city

import database.CityEntity
import kotlinx.coroutines.flow.Flow

interface ICityDao {

    suspend fun insertCityList(cityList: List<CityEntity>)

    fun observeCityList(): Flow<List<CityEntity>>

    fun observeCityByUuid(uuid: String): Flow<CityEntity?>
}