package com.bunbeauty.domain.repository.district

import com.bunbeauty.data.model.DistrictEntity
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun insert(districtEntity: DistrictEntity): Long
    suspend fun update(districtEntity: DistrictEntity)
    fun getDistricts(): Flow<List<DistrictEntity>>
}