package com.example.data_api.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.CafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    // OBSERVE
    @Query("SELECT * FROM CafeEntity")
    fun observeCafeList(): Flow<List<CafeEntity>>

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    fun observeCafeByUuid(uuid: String): Flow<CafeEntity>

    // GET

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    suspend fun getCafeByUuid(uuid: String): CafeEntity

}