package com.bunbeauty.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.BaseDao

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    @Query("SELECT * FROM CafeEntity")
    fun getCafeListLiveData(): LiveData<List<Cafe>>

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