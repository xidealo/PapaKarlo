package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.DistrictDao
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import com.bunbeauty.domain.repo.DistrictRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistrictRepository @Inject constructor(
    private val districtDao: DistrictDao
) : DistrictRepo {

    override suspend fun update(districtEntity: DistrictEntity) {
        districtDao.update(districtEntity)
    }

    override suspend fun getDistrictByUuid(uuid: String): DistrictEntity {
        return districtDao.getDistrictByUuid(uuid)
    }

    override fun getDistricts(): Flow<List<DistrictEntity>> {
        return districtDao.observeDistrictList()
    }
}