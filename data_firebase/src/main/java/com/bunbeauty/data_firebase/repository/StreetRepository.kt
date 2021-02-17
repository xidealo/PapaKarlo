package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.StreetDao
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.StreetRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StreetRepository @Inject constructor(
    private val streetDao: StreetDao,
    private val streetMapper: com.example.domain_firebase.mapper.IStreetMapper,
) : StreetRepo {

    override suspend fun getStreets(): List<Street> {
        return streetDao.getStreetList().map(streetMapper::toUIModel)
    }

    override suspend fun refreshStreetList(selectedCityUuid: String) {
        //TODO
    }

    override suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street? {
        return null
    }

    override fun observeStreetListByCityUuid(cityUuid: String): Flow<List<Street>> {
        return flow { }
    }
}