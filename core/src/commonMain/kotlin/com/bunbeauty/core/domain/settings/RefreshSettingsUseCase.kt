package com.bunbeauty.core.domain.settings

import com.bunbeauty.core.domain.repo.SettingsRepo
import com.bunbeauty.core.model.Settings

class RefreshSettingsUseCase(
    private val settingsRepo: SettingsRepo,
) {
    suspend operator fun invoke(): Settings? = settingsRepo.refreshSettings()
}
