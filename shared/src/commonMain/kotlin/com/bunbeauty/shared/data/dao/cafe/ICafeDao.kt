package com.bunbeauty.shared.data.dao.cafe

import com.bunbeauty.shared.db.CafeEntity
import com.bunbeauty.shared.db.SelectedCafeUuidEntity
import kotlinx.coroutines.flow.Flow


interface ICafeDao {

    suspend fun insertCafeList(cafeList: List<CafeEntity>)
    suspend fun insertSelectedCafeUuid(selectedCafeUuidEntity: SelectedCafeUuidEntity)

    suspend fun getSelectedCafeByUserAndCityUuid(userUuid: String, cityUuid: String): CafeEntity?
    suspend fun getFirstCafeByCityUuid(cityUuid: String): CafeEntity?

    fun observeCafeListByCityUuid(cityUuid: String): Flow<List<CafeEntity>>
    fun observeCafeByUuid(uuid: String): Flow<CafeEntity?>
    fun observeSelectedCafeByUserAndCityUuid(userUuid: String, cityUuid: String): Flow<CafeEntity?>
    fun observeFirstCafeByCityUuid(cityUuid: String): Flow<CafeEntity?>

    suspend fun getCafeListByCityUuid(cityUuid: String): List<CafeEntity>
    suspend fun getCafeByUuid(uuid: String): CafeEntity?
}