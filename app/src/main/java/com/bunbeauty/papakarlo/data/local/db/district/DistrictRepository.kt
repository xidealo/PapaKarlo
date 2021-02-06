package com.bunbeauty.papakarlo.data.local.db.district

import com.bunbeauty.papakarlo.data.model.DistrictEntity
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