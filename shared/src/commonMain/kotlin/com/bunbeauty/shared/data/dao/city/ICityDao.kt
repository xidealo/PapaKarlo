package com.bunbeauty.shared.data.dao.city

import com.bunbeauty.shared.db.CityEntity
import kotlinx.coroutines.flow.Flow

interface ICityDao {
    suspend fun insertCityList(cityList: List<CityEntity>)

    suspend fun getCityList(): List<CityEntity>

    suspend fun getCityByUuid(uuid: String): CityEntity?

    fun observeCityList(): Flow<List<CityEntity>>

    fun observeCityByUuid(uuid: String): Flow<CityEntity?>
}
