package com.example.data_api.repository

import com.bunbeauty.common.Logger.CITY_TAG
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CityRepo
import com.example.data_api.dao.CityDao
import com.example.data_api.handleListResult
import com.example.domain_api.mapper.ICityMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val cityDao: CityDao,
    private val cityMapper: ICityMapper,
) : CityRepo {

    override suspend fun refreshCityList() {
        apiRepo.getCityList().handleListResult(CITY_TAG) { cityList ->
            if (cityList != null) {
                val cityEntityList = cityList.map(cityMapper::toEntityModel)
                cityDao.insertAll(cityEntityList)
            }
        }
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityDao.observeCityList().mapListFlow(cityMapper::toModel)
    }

    override fun observeCityByUuid(cityUuid: String): Flow<City?> {
        return cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toModel)
    }
}