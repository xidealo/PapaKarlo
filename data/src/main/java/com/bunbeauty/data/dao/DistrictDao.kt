package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrictDao : BaseDao<DistrictEntity> {

    @Query("SELECT * FROM DistrictEntity")
    fun observeDistrictList(): Flow<List<DistrictEntity>>

    @Query("SELECT * FROM DistrictEntity WHERE uuid = :uuid")
    suspend fun getDistrictByUuid(uuid: String): DistrictEntity
}