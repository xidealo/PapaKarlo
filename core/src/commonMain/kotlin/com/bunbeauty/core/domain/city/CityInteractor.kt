package com.bunbeauty.core.domain.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.model.city.SelectableCity
import com.bunbeauty.core.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityInteractor(
    private val cityRepo: CityRepo,
) : ICityInteractor {
    override suspend fun getCityList(): List<City>? = cityRepo.getCityList().ifEmpty { null }

    override suspend fun checkIsCitySelected(): Boolean = cityRepo.getSelectedCityUuid() != null

    override suspend fun saveSelectedCity(city: City) {
        cityRepo.saveSelectedCityUuid(cityUuid = city.uuid)
    }

    override fun observeCityList(): Flow<List<SelectableCity>> =
        cityRepo
            .observeCityList()
            .map { cityList ->
                cityList.map { city ->
                    SelectableCity(
                        city = city,
                        isSelected = cityRepo.getSelectedCityUuid() == city.uuid,
                    )
                }
            }
}
