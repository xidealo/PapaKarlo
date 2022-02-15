package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.CITY_TAG
import com.bunbeauty.data.dao.city.ICityDao
import com.bunbeauty.data.handleListResultAndReturn
import com.bunbeauty.data.mapper.city.ICityMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow

class CityRepository(
    private val apiRepo: ApiRepo,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
) : CityRepo {

    private var cityListCache: List<City>? = null

    override suspend fun getCityList(): List<City> {
        return cityListCache ?: apiRepo.getCityList().handleListResultAndReturn(
            tag = CITY_TAG,
            onError = {
                cityDao.getCityList().map(cityMapper::toCity)
            },
            onSuccess = { cityServerList ->
                cityDao.insertCityList(cityServerList.map(cityMapper::toCityEntity))
                cityServerList.map(cityMapper::toCity).also { cityList ->
                    cityListCache = cityList
                }
            }
        )
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityDao.observeCityList().mapListFlow(cityMapper::toCity)
    }

    override fun observeCityByUuid(cityUuid: String): Flow<City?> {
        return cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toCity)
    }
}