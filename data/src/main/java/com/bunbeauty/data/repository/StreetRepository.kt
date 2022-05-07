package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.STREET_TAG
import com.bunbeauty.data.dao.street.IStreetDao
import com.bunbeauty.data.mapper.street.IStreetMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.shared.domain.model.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

class StreetRepository(
    private val apiRepo: ApiRepo,
    private val streetDao: IStreetDao,
    private val streetMapper: IStreetMapper,
) : CacheListRepository<Street>(), StreetRepo {

    override val tag: String = STREET_TAG

    override suspend fun getStreetList(selectedCityUuid: String): List<Street> {
        return getCacheOrListData(
            isCacheValid = { cacheList ->
                cacheList.all { cafe ->
                    cafe.cityUuid == selectedCityUuid
                }
            },
            onApiRequest = {
                apiRepo.getStreetListByCityUuid(selectedCityUuid)
            },
            onLocalRequest = {
                streetDao.getStreetListByCityUuid(selectedCityUuid).map(streetMapper::toStreet)
            },
            onSaveLocally = { streetServerList ->
                streetDao.insertStreetList(streetServerList.map(streetMapper::toStreetEntity))
            },
            serverToDomainModel = streetMapper::toStreet
        )
    }

    override suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street? {
        return streetDao.getStreetByNameAndCityUuid(name, cityUuid)?.let { streetEntity ->
            streetMapper.toStreet(streetEntity)
        }
    }
}