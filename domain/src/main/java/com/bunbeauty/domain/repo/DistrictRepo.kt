package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.entity.address.DistrictEntity
import kotlinx.coroutines.flow.Flow

interface DistrictRepo {
    suspend fun update(districtEntity: DistrictEntity)
    suspend fun getDistrictByUuid(uuid: String): DistrictEntity
    fun getDistricts(): Flow<List<DistrictEntity>>
}