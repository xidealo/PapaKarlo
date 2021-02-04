package com.bunbeauty.papakarlo.data.local.db.district

import com.bunbeauty.papakarlo.data.model.District
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistrictRepository @Inject constructor(
    private val districtDao: DistrictDao
) : DistrictRepo {

    override suspend fun insert(district: District): Long {
        return districtDao.insert(district)
    }

    override suspend fun update(district: District) {
        districtDao.update(district)
    }

    override fun getDistricts(): Flow<List<District>> {
        return districtDao.getDistricts()
    }
}