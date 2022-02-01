package com.bunbeauty.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT * FROM CityEntity")
    fun observeCityList(): Flow<List<CityEntity>>

    @Query("SELECT * FROM CityEntity WHERE uuid = :uuid")
    fun observeCityByUuid(uuid: String): Flow<CityEntity?>
}