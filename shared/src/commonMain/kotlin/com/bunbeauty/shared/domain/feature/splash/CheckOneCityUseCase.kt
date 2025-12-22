package com.bunbeauty.shared.domain.feature.splash

import com.bunbeauty.shared.domain.repo.CityRepo

private const val ONE_CITY_COUNT = 1

class CheckOneCityUseCase(
    private val cityRepo: CityRepo,
) {
    suspend operator fun invoke(): Boolean = cityRepo.getCityList().size == ONE_CITY_COUNT
}
