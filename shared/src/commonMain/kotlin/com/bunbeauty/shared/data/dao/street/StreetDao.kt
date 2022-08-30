package com.bunbeauty.shared.data.dao.street

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.StreetEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
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

    override suspend fun getStreetListByCityUuid(cityUuid: String): List<StreetEntity> {
        return streetEntityQueries.getStreetListByCityUuid(cityUuid).executeAsList()
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