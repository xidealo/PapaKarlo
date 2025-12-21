package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.city.ICityDao
import com.bunbeauty.shared.data.mapper.city.ICityMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow

class CityRepository(
    private val networkConnector: NetworkConnector,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
) : CacheListRepository<City>(),
    CityRepo {
    override val tag: String = "CITY_TAG"

    override suspend fun getCityList(): List<City> =
        getCacheOrListData(
            onApiRequest = networkConnector::getCityList,
            onLocalRequest = {
                cityDao.getCityList().map(cityMapper::toCity)
            },
            onSaveLocally = { cityServerList ->
                cityDao.insertCityList(cityServerList.map(cityMapper::toCityEntity))
            },
            serverToDomainModel = cityMapper::toCity,
        )

    override suspend fun getCityByUuid(cityUuid: String): City? =
        cityDao.getCityByUuid(cityUuid)?.let { cityEntity ->
            cityMapper.toCity(cityEntity)
        }

    override fun observeCityList(): Flow<List<City>> = cityDao.observeCityList().mapListFlow(cityMapper::toCity)

    override fun observeCityByUuid(cityUuid: String): Flow<City?> = cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toCity)
}
