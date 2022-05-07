package com.bunbeauty.domain.interactor.city

import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.repo.CityRepo
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class CityInteractor(
    private val dataStoreRepo: DataStoreRepo,
    private val cityRepo: CityRepo
) : ICityInteractor {

    override suspend fun getCityList(): List<City>? {
        return cityRepo.getCityList().ifEmpty { null }
    }

    override suspend fun checkIsCitySelected(): Boolean {
        return dataStoreRepo.getSelectedCityUuid() != null
    }

    override suspend fun saveSelectedCity(city: City) {
        dataStoreRepo.saveSelectedCityUuid(city.uuid, city.timeZone)
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