package com.bunbeauty.papakarlo.data.local.db.district

import com.bunbeauty.papakarlo.data.model.DistrictEntity
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun insert(districtEntity: DistrictEntity): Long
    suspend fun update(districtEntity: DistrictEntity)
    fun getDistricts(): Flow<List<DistrictEntity>>
}