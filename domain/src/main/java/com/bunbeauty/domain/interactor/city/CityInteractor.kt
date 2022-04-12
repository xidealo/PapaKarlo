package com.bunbeauty.domain.interactor.city

import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.worker.IStreetWorkerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class CityInteractor(
    private val dataStoreRepo: DataStoreRepo,
    private val cityRepo: CityRepo,
    private val streetWorkerUtil: IStreetWorkerUtil
) : ICityInteractor {

    override suspend fun getCityList(): List<City>? {
        return cityRepo.getCityList().ifEmpty { null }
    }

    override suspend fun checkIsCitySelected(): Boolean {
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()
        if (selectedCityUuid != null) {
            streetWorkerUtil.refreshStreetList(selectedCityUuid)
        }

        return selectedCityUuid != null
    }

    override suspend fun saveSelectedCity(city: City) {
        dataStoreRepo.saveSelectedCityUuid(city.uuid, city.timeZone)
        streetWorkerUtil.refreshStreetList(city.uuid)
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityRepo.observeCityList()
    }

    override fun observeSelectedCity(): Flow<City?> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cityRepo.observeCityByUuid(selectedCityUuid ?: "")
        }
    }
}