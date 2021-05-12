package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.StreetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetDao : BaseDao<StreetEntity> {

    @Query("SELECT * FROM StreetEntity WHERE cityUuid = :cityUuid")
    fun getStreetListByCityUuid(cityUuid: String): List<StreetEntity>

    @Query("SELECT * FROM StreetEntity WHERE cityUuid = :cityUuid")
    fun observeStreetListByCityUuid(cityUuid: String): Flow<List<StreetEntity>>
}