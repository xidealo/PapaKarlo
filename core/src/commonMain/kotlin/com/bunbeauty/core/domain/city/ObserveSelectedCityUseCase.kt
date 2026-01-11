package com.bunbeauty.core.domain.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow

class ObserveSelectedCityUseCase(
    private val cityRepo: CityRepo,
) {
    operator fun invoke(): Flow<City?> = cityRepo.observeSelectedCity()
}
