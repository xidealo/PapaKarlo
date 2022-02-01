package com.bunbeauty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.cafe.CafeEntity
import com.bunbeauty.data.database.entity.cafe.SelectedCafeUuidEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    // INSERT

    @Insert(onConflict = REPLACE)
    suspend fun insertSelectedCafeUuid(selectedCafeUuidEntity: SelectedCafeUuidEntity)

    // OBSERVE

    @Query("SELECT * FROM CafeEntity WHERE cityUuid = :cityUuid")
    fun observeCafeListByCityUuid(cityUuid: String): Flow<List<CafeEntity>>

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    fun observeCafeByUuid(uuid: String): Flow<CafeEntity?>

    @Query(
        "SELECT * FROM CafeEntity C " +
                "JOIN SelectedCafeUuidEntity SC ON C.uuid == SC.cafeUuid " +
                "WHERE SC.userUuid = :userUuid AND SC.cityUuid = :cityUuid"
    )
    fun observeSelectedCafeByUserAndCityUuid(userUuid: String, cityUuid: String): Flow<CafeEntity?>

    @Query("SELECT * FROM CafeEntity " +
            "WHERE cityUuid = :cityUuid " +
            "ORDER BY uuid DESC " +
            "LIMIT 1")
    fun observeFirstCafeByCityUuid(cityUuid: String): Flow<CafeEntity?>

    // GET

    @Query("SELECT * FROM CafeEntity WHERE cityUuid = :cityUuid")
    suspend fun getCafeListByCityUuid(cityUuid: String): List<CafeEntity>

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    suspend fun getCafeByUuid(uuid: String): CafeEntity?

}