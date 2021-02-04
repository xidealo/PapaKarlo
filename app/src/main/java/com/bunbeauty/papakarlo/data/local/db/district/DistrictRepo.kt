package com.bunbeauty.papakarlo.data.local.db.district

import com.bunbeauty.papakarlo.data.model.District
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun insert(district: District): Long
    suspend fun update(district: District)
    fun getDistricts(): Flow<List<District>>
}