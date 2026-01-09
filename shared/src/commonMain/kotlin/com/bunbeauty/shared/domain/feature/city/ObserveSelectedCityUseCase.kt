package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveSelectedCityUseCase(
    private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    operator fun invoke(): Flow<City?> =
        dataStoreRepo.selectedCityUuid.map { cityUuid ->
            cityUuid?.let {
                cityRepo.getCityByUuid(cityUuid)
            }
        }
}
