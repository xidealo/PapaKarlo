package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.city.ICityDao
import com.bunbeauty.shared.data.mapper.city.ICityMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.repository.base.CacheListRepository
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo

class CityRepository(
    private val networkConnector: NetworkConnector,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
) : CacheListRepository<City>(), CityRepo {

    override val tag: String = "CITY_TAG"

    override suspend fun getCityList(): List<City> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getCityList,
            onLocalRequest = {
                cityDao.getCityList().map(cityMapper::toCity)
            },
            onSaveLocally = { cityServerList ->
                cityDao.insertCityList(cityServerList.map(cityMapper::toCityEntity))
            },
            serverToDomainModel = cityMapper::toCity
        )
    }

    override suspend fun getCityByUuid(cityUuid: String): City? {
        return cityDao.getCityByUuid(uuid = cityUuid)?.let { cityEntity ->
            cityMapper.toCity(cityEntity = cityEntity)
        }
    }
}
