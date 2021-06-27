package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.DistrictEntity
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun insert(districtEntity: DistrictEntity): Long
    suspend fun update(districtEntity: DistrictEntity)
    fun getDistricts(): Flow<List<DistrictEntity>>
}