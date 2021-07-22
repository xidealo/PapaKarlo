package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.DistrictEntity
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun insert(districtEntity: DistrictEntity)
    suspend fun update(districtEntity: DistrictEntity)
    fun getDistricts(): Flow<List<DistrictEntity>>
}