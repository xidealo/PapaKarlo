package com.bunbeauty.data.sql_delight.dao.cafe

import database.CafeEntity
import database.SelectedCafeUuidEntity
import kotlinx.coroutines.flow.Flow


interface ICafeDao {

    suspend fun insertCafeList(cafeList: List<CafeEntity>)
    suspend fun insertSelectedCafeUuid(selectedCafeUuidEntity: SelectedCafeUuidEntity)

    fun observeCafeListByCityUuid(cityUuid: String): Flow<List<CafeEntity>>
    fun observeCafeByUuid(uuid: String): Flow<CafeEntity?>
    fun observeSelectedCafeByUserAndCityUuid(userUuid: String, cityUuid: String): Flow<CafeEntity?>
    fun observeFirstCafeByCityUuid(cityUuid: String): Flow<CafeEntity?>

    suspend fun getCafeListByCityUuid(cityUuid: String): List<CafeEntity>
    suspend fun getCafeByUuid(uuid: String): CafeEntity?
}