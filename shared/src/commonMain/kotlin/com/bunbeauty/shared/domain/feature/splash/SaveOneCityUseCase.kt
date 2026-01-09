package com.bunbeauty.shared.domain.feature.splash

import com.bunbeauty.core.domain.exeptions.NoCityException
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.CityRepo

class SaveOneCityUseCase(
    private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke() {
        val city = cityRepo.getCityList().firstOrNull() ?: throw NoCityException()
        dataStoreRepo.saveSelectedCityUuid(city.uuid)
    }
}
