package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo

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
}
