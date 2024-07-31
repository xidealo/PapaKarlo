package com.bunbeauty.shared.domain.feature.settings

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.SettingsRepository
import com.bunbeauty.shared.domain.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveSettingsUseCase(
    private val settingsRepository: SettingsRepository,
    private val dataStoreRepo: DataStoreRepo
) {

    suspend operator fun invoke(): Flow<Settings?> {
        return dataStoreRepo.getToken()?.let { token ->
            settingsRepository.observeSettings(token)
        } ?: flow {
            emit(null)
        }
    }
}
