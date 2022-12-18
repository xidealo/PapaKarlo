package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.SettingsRepository
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.repo.CityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SaveSelectedCityUseCase(
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(cityUuid: String) {
        return dataStoreRepo.saveSelectedCityUuid(cityUuid)
    }
}