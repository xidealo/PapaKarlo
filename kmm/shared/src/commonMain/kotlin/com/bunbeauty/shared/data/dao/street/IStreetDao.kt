package com.bunbeauty.shared.data.dao.street

import database.StreetEntity
import kotlinx.coroutines.flow.Flow


interface IStreetDao {

    suspend fun insertStreetList(streetList: List<StreetEntity>)

    suspend fun getStreetListByCityUuid(cityUuid: String): List<StreetEntity>

    fun observeStreetListByCityUuid(cityUuid: String): Flow<List<StreetEntity>>

    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): StreetEntity?
}