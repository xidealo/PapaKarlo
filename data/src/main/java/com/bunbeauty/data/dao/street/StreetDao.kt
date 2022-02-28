package com.bunbeauty.data.dao.street

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.StreetEntity
import kotlinx.coroutines.flow.Flow

class StreetDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IStreetDao {

    private val streetEntityQueries = foodDeliveryDatabase.streetEntityQueries

    override suspend fun insertStreetList(streetList: List<StreetEntity>) {
        streetEntityQueries.transaction {
            streetList.forEach { streetEntity ->
                streetEntityQueries.insertStreet(
                    uuid = streetEntity.uuid,
                    name = streetEntity.name,
                    cityUuid = streetEntity.cityUuid,
                )
            }
        }
    }

    override fun observeStreetListByCityUuid(cityUuid: String): Flow<List<StreetEntity>> {
        return streetEntityQueries.getStreetListByCityUuid(cityUuid).asFlow().mapToList()
    }

    override suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): StreetEntity? {
        return streetEntityQueries.getStreetByNameAndCityUuid(
            name = name,
            cityUuid = cityUuid
        ).executeAsOneOrNull()
    }

}