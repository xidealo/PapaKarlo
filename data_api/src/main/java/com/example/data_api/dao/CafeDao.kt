package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.CafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    // OBSERVE
    @Query("SELECT * FROM CafeEntity WHERE cityUuid = :cityUuid")
    fun observeCafeListByCityUuid(cityUuid: String): Flow<List<CafeEntity>>

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    fun observeCafeByUuid(uuid: String): Flow<CafeEntity>

    // GET

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    suspend fun getCafeByUuid(uuid: String): CafeEntity

}