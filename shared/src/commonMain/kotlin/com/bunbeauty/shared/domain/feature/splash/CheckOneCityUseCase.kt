package com.bunbeauty.shared.domain.feature.splash

import com.bunbeauty.shared.domain.repo.CityRepo

class CheckOneCityUseCase(
    private val cityRepo: CityRepo,
) {
    suspend operator fun invoke(): Boolean {
        return cityRepo.getCityList().size == 1
    }
}
