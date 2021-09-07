package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.StreetDao
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.StreetRepo
import javax.inject.Inject

class StreetRepository @Inject constructor(
    private val streetDao: StreetDao,
    private val streetMapper: com.example.domain_firebase.mapper.IStreetMapper,
) : StreetRepo {

    override suspend fun getStreets(): List<Street> {
        return streetDao.getStreetList().map(streetMapper::toUIModel)
    }
}