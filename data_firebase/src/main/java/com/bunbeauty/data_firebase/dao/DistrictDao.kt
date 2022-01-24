package com.bunbeauty.data_firebase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.data.BaseDao
import com.example.domain_firebase.model.entity.address.DistrictEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrictDao : BaseDao<DistrictEntity> {

    @Query("SELECT * FROM DistrictEntity")
    fun observeDistrictList(): Flow<List<DistrictEntity>>

    @Query("SELECT * FROM DistrictEntity WHERE uuid = :uuid")
    suspend fun getDistrictByUuid(uuid: String): DistrictEntity
}