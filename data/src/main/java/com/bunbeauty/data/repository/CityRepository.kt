package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.CITY_TAG
import com.bunbeauty.data.dao.city.ICityDao
import com.bunbeauty.data.handleListResult
import com.bunbeauty.data.mapper.city.ICityMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow

class CityRepository constructor(
    private val apiRepo: ApiRepo,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
) : CityRepo {

    override suspend fun refreshCityList() {
        apiRepo.getCityList().handleListResult(CITY_TAG) { cityList ->
            if (cityList != null) {
                val cityEntityList = cityList.map(cityMapper::toCityEntity)
                cityDao.insertCityList(cityEntityList)
            }
        }
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityDao.observeCityList().mapListFlow(cityMapper::toCity)
    }

    override fun observeCityByUuid(cityUuid: String): Flow<City?> {
        return cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toCity)
    }
}