package com.example.data_api.repository

import com.bunbeauty.common.Logger.STREET_TAG
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.example.data_api.dao.StreetDao
import com.example.data_api.handleListResult
import com.example.domain_api.mapper.IStreetMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreetRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val streetDao: StreetDao,
    private val dataStoreRepo: DataStoreRepo,
    private val streetMapper: IStreetMapper,
) : StreetRepo {

    override suspend fun refreshStreetList(selectedCityUuid: String) {
        apiRepo.getStreetListByCityUuid(selectedCityUuid)
            .handleListResult(STREET_TAG) { streetList ->
                if (streetList != null) {
                    streetDao.insertAll(streetList.map(streetMapper::toEntityModel))
                }
            }
    }

    override suspend fun getStreets(): List<Street> {
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()
        return if (selectedCityUuid != null) {
            streetDao.getStreetListByCityUuid(selectedCityUuid).map(streetMapper::toModel)
        } else {
            emptyList()
        }
    }

    override suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street? {
        return streetDao.getStreetByNameAndCityUuid(name, cityUuid)?.let { streetEntity ->
            streetMapper.toModel(streetEntity)
        }
    }

    override fun observeStreetListByCityUuid(cityUuid: String): Flow<List<Street>> {
        return streetDao.observeStreetListByCityUuid(cityUuid).mapListFlow(streetMapper::toModel)
    }
}