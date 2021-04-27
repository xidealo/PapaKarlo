package com.bunbeauty.domain.repository.district

import com.bunbeauty.data.dao.DistrictDao
import com.bunbeauty.data.model.DistrictEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistrictRepository @Inject constructor(
    private val districtDao: DistrictDao
) : DistrictRepo {

    override suspend fun insert(districtEntity: DistrictEntity): Long {
        return districtDao.insert(districtEntity)
    }

    override suspend fun update(districtEntity: DistrictEntity) {
        districtDao.update(districtEntity)
    }

    override fun getDistricts(): Flow<List<DistrictEntity>> {
        return districtDao.getDistricts()
    }
}