package com.bunbeauty.shared.data.dao.cafe

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.CafeEntity
import database.SelectedCafeUuidEntity
import kotlinx.coroutines.flow.Flow

class CafeDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ICafeDao {

    private val cityEntityQueries = foodDeliveryDatabase.cafeEntityQueries
    private val selectedCafeUuidEntityQueries = foodDeliveryDatabase.selectedCafeUuidEntityQueries

    override suspend fun insertCafeList(cafeList: List<CafeEntity>) {
        cityEntityQueries.transaction {
            cafeList.onEach { cafeEntity ->
                cityEntityQueries.insertCafe(
                    uuid = cafeEntity.uuid,
                    fromTime = cafeEntity.fromTime,
                    toTime = cafeEntity.toTime,
                    offset = cafeEntity.offset,
                    phone = cafeEntity.phone,
                    latitude = cafeEntity.latitude,
                    longitude = cafeEntity.longitude,
                    address = cafeEntity.address,
                    cityUuid = cafeEntity.cityUuid,
                    isVisible = cafeEntity.isVisible,
                )
            }
        }
    }

    override suspend fun insertSelectedCafeUuid(selectedCafeUuidEntity: SelectedCafeUuidEntity) {
        selectedCafeUuidEntityQueries.insertSelectedCafeUuid(
            userUuid = selectedCafeUuidEntity.userUuid,
            cityUuid = selectedCafeUuidEntity.cityUuid,
            cafeUuid = selectedCafeUuidEntity.cafeUuid,
        )
    }

    override fun observeCafeListByCityUuid(cityUuid: String): Flow<List<CafeEntity>> {
        return cityEntityQueries.getCafeListByCityUuid(cityUuid).asFlow().mapToList()
    }

    override fun observeCafeByUuid(uuid: String): Flow<CafeEntity?> {
        return cityEntityQueries.getCafeByUuid(uuid).asFlow().mapToOneOrNull()
    }

    override fun observeSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<CafeEntity?> {
        return cityEntityQueries.getSelectedCafeByUserAndCityUuid(userUuid, cityUuid).asFlow()
            .mapToOneOrNull()
    }

    override fun observeFirstCafeByCityUuid(cityUuid: String): Flow<CafeEntity?> {
        return cityEntityQueries.getFirstCafeByCityUuid(cityUuid).asFlow().mapToOneOrNull()
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): List<CafeEntity> {
        return cityEntityQueries.getCafeListByCityUuid(cityUuid).executeAsList()
    }

    override suspend fun getCafeByUuid(uuid: String): CafeEntity? {
        return cityEntityQueries.getCafeByUuid(uuid).executeAsOneOrNull()
    }
}