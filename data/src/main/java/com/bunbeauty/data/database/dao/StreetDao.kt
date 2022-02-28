package com.bunbeauty.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.StreetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetDao : BaseDao<StreetEntity> {

    @Query("SELECT * FROM StreetEntity WHERE name = :name AND cityUuid = :cityUuid")
    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): StreetEntity?

    @Query("SELECT * FROM StreetEntity WHERE cityUuid = :cityUuid")
    fun observeStreetListByCityUuid(cityUuid: String): Flow<List<StreetEntity>>
}