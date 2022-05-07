package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.CITY_TAG
import com.bunbeauty.data.dao.city.ICityDao
import com.bunbeauty.data.mapper.city.ICityMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow

class CityRepository(
    private val apiRepo: ApiRepo,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
) : CacheListRepository<City>(), CityRepo {

    override val tag: String = CITY_TAG

    override suspend fun getCityList(): List<City> {
        return getCacheOrListData(
            onApiRequest = apiRepo::getCityList,
            onLocalRequest = {
                cityDao.getCityList().map(cityMapper::toCity)
            },
            onSaveLocally = { cityServerList ->
                cityDao.insertCityList(cityServerList.map(cityMapper::toCityEntity))
            },
            serverToDomainModel = cityMapper::toCity
        )
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityDao.observeCityList().mapListFlow(cityMapper::toCity)
    }

    override fun observeCityByUuid(cityUuid: String): Flow<City?> {
        return cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toCity)
    }
}