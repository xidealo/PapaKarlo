package com.bunbeauty.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    @Query("SELECT * FROM CafeEntity")
    fun getCafeListFlow(): Flow<List<Cafe>>

    @Query("SELECT * FROM CafeEntity")
    fun getCafeList(): List<Cafe>

    @Query("SELECT * FROM CafeEntity WHERE id = :id")
    fun getCafeEntityById(id: String): CafeEntity?

    @Query("SELECT * FROM CafeEntity WHERE id = :id")
    fun getCafeById(id: String): LiveData<Cafe>

    @Query("SELECT CafeEntity.* FROM CafeEntity, DistrictEntity WHERE DistrictEntity.id = :districtId AND CafeEntity.id = DistrictEntity.cafeId")
    fun getCafeEntityByDistrict(districtId: String): CafeEntity

    @Transaction
    @Query("DELETE FROM CafeEntity")
    fun deleteAll()
}