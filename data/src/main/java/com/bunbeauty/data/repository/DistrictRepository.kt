package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.DistrictDao
import com.bunbeauty.domain.model.ui.DistrictEntity
import com.bunbeauty.domain.repo.DistrictRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistrictRepository @Inject constructor(
    private val districtDao: DistrictDao
) : DistrictRepo {

    override suspend fun insert(districtEntity: DistrictEntity) {
        districtDao.insert(districtEntity)
    }

    override suspend fun update(districtEntity: DistrictEntity) {
        districtDao.update(districtEntity)
    }

    override fun getDistricts(): Flow<List<DistrictEntity>> {
        return districtDao.getDistricts()
    }
}