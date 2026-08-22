package com.bunbeauty.shared.data.dao.city

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bunbeauty.shared.db.CityEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import kotlinx.coroutines.Dispatchers
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

    override suspend fun getCityList(): List<CityEntity> = cityEntityQueries.getCityList().awaitAsList()

    override suspend fun getCityByUuid(uuid: String): CityEntity? = cityEntityQueries.getCityByUuid(uuid).awaitAsOneOrNull()

    override fun observeCityByUuid(uuid: String): Flow<CityEntity?> =
        cityEntityQueries
            .getCityByUuid(uuid)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override fun observeCityList(): Flow<List<CityEntity>> =
        cityEntityQueries
            .getCityList()
            .asFlow()
            .mapToList(Dispatchers.Default)
}
