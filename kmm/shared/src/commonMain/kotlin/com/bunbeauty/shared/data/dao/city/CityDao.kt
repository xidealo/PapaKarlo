package com.bunbeauty.shared.data.dao.city

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.CityEntity
import kotlinx.coroutines.flow.Flow

class CityDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ICityDao {

    private val cityEntityQueries = foodDeliveryDatabase.cityEntityQueries

    override suspend fun insertCityList(cityList: List<CityEntity>) {
        cityEntityQueries.transaction {
            cityList.forEach { cityEntity ->
                cityEntityQueries.insertCity(
                    uuid = cityEntity.uuid,
                    name = cityEntity.name,
                    timeZone = cityEntity.timeZone,
                    isVisible = cityEntity.isVisible
                )
            }
        }
    }

    override fun getCityList(): List<CityEntity> {
        return cityEntityQueries.getCityList().executeAsList()
    }

    override fun observeCityByUuid(uuid: String): Flow<CityEntity?> {
        return cityEntityQueries.getCityByUuid(uuid).asFlow().mapToOneOrNull()
    }

    override fun observeCityList(): Flow<List<CityEntity>> {
        return cityEntityQueries.getCityList().asFlow().mapToList()
    }
}