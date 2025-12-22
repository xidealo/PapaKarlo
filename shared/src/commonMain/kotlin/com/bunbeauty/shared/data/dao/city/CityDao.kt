package com.bunbeauty.shared.data.dao.city

import com.bunbeauty.shared.db.CityEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class CityDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : ICityDao {
    private val cityEntityQueries = foodDeliveryDatabase.cityEntityQueries

    override suspend fun insertCityList(cityList: List<CityEntity>) {
        cityEntityQueries.transaction {
            cityList.forEach { cityEntity ->
                cityEntityQueries.insertCity(
                    uuid = cityEntity.uuid,
                    name = cityEntity.name,
                    timeZone = cityEntity.timeZone,
                    isVisible = cityEntity.isVisible,
                )
            }
        }
    }

    override suspend fun getCityList(): List<CityEntity> = cityEntityQueries.getCityList().executeAsList()

    override suspend fun getCityByUuid(uuid: String): CityEntity? = cityEntityQueries.getCityByUuid(uuid).executeAsOneOrNull()

    override fun observeCityByUuid(uuid: String): Flow<CityEntity?> = cityEntityQueries.getCityByUuid(uuid).asFlow().mapToOneOrNull()

    override fun observeCityList(): Flow<List<CityEntity>> = cityEntityQueries.getCityList().asFlow().mapToList()
}
