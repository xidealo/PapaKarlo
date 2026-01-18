package com.bunbeauty.core.domain.settings

import com.bunbeauty.core.domain.repo.SettingsRepo
import com.bunbeauty.core.model.Settings
import kotlinx.coroutines.flow.Flow

class ObserveSettingsUseCase(
    private val settingsRepository: SettingsRepo,
) {
    suspend operator fun invoke(): Flow<Settings?> = settingsRepository.observeSettings()

}
