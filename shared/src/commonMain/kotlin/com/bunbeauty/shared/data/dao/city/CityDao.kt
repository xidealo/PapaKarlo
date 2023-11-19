package com.bunbeauty.shared.data.dao.city

import app.cash.sqldelight.coroutines.asFlow
import com.bunbeauty.shared.db.CityEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.extension.mapToList
import com.bunbeauty.shared.extension.mapToOneOrNull
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

    override fun getCityByUuid(uuid: String): CityEntity? {
        return cityEntityQueries.getCityByUuid(uuid).executeAsOneOrNull()
    }

    override fun observeCityByUuid(uuid: String): Flow<CityEntity?> {
        return cityEntityQueries.getCityByUuid(uuid)
            .asFlow()
            .mapToOneOrNull()
    }

    override fun observeCityList(): Flow<List<CityEntity>> {
        return cityEntityQueries.getCityList()
            .asFlow()
            .mapToList()
    }
}