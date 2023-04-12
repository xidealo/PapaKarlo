package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.repo.CityRepo
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
        dataStoreRepo.saveSelectedCityUuid(city.uuid)
    }

    override fun observeCityList(): CommonFlow<List<City>> {
        return cityRepo.observeCityList().asCommonFlow()
    }

    override fun observeSelectedCity(): Flow<City?> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cityRepo.observeCityByUuid(selectedCityUuid ?: "")
        }
    }
}