package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.street.IStreetDao
import com.bunbeauty.shared.data.mapper.street.IStreetMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.Street
import com.bunbeauty.shared.domain.repo.StreetRepo

class StreetRepository(
    private val networkConnector: NetworkConnector,
    private val streetDao: IStreetDao,
    private val streetMapper: IStreetMapper,
) : CacheListRepository<Street>(), StreetRepo {

    override val tag: String = "STREET_TAG"

    override suspend fun getStreetList(selectedCityUuid: String): List<Street> {
        return getCacheOrListData(
            isCacheValid = { cacheList ->
                cacheList.all { street ->
                    street.cityUuid == selectedCityUuid
                }
            },
            onApiRequest = {
                networkConnector.getStreetListByCityUuid(selectedCityUuid)
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