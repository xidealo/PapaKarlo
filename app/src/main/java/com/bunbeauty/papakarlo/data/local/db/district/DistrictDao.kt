package com.bunbeauty.papakarlo.data.local.db.district

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.District
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrictDao : BaseDao<District> {
    @Query("SELECT * FROM District")
    fun getDistricts(): Flow<List<District>>
}