package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.CityRepo
import com.bunbeauty.core.model.city.City
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.city.ICityDao
import com.bunbeauty.shared.data.mapper.city.ICityMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CityRepository(
    private val networkConnector: NetworkConnector,
    private val cityDao: ICityDao,
    private val cityMapper: ICityMapper,
    private val dataStoreRepo: DataStoreRepo,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSelectedCity(): Flow<City?> =
        dataStoreRepo.selectedCityUuid.flatMapLatest { cityUuid ->
            cityUuid?.let {
                observeCityByUuid(it)
            } ?: flow { emit(null) }
        }

    override suspend fun getSelectedCityUuid(): String? = dataStoreRepo.getSelectedCityUuid()

    override suspend fun saveSelectedCityUuid(cityUuid: String) {
        dataStoreRepo.saveSelectedCityUuid(cityUuid = cityUuid)
    }

    override fun observeCityList(): Flow<List<City>> = cityDao.observeCityList().mapListFlow(cityMapper::toCity)

    override fun observeCityByUuid(cityUuid: String): Flow<City?> = cityDao.observeCityByUuid(cityUuid).mapFlow(cityMapper::toCity)
}
