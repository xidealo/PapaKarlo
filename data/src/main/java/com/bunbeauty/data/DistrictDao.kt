package com.bunbeauty.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.data.model.DistrictEntity
import com.bunbeauty.data.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrictDao : BaseDao<DistrictEntity> {
    @Query("SELECT * FROM DistrictEntity")
    fun getDistricts(): Flow<List<DistrictEntity>>

    @Transaction
    @Query("DELETE FROM DistrictEntity")
    fun deleteAll()
}