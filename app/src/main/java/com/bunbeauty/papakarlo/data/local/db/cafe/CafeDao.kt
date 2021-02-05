package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.Street
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import kotlinx.coroutines.Deferred

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    @Query("SELECT * FROM CafeEntity")
    fun getCafeList(): LiveData<List<Cafe>>

    @Query("SELECT * FROM CafeEntity WHERE id = :id")
    fun getCafeEntityById(id: String): CafeEntity?

    @Query("SELECT * FROM CafeEntity WHERE id = :id")
    fun getCafeById(id: String): LiveData<Cafe>

    @Query("SELECT CafeEntity.* FROM CafeEntity, District WHERE District.id = :districtId AND CafeEntity.id = District.cafeId")
    fun getCafeEntityByDistrict(districtId: String): CafeEntity
}