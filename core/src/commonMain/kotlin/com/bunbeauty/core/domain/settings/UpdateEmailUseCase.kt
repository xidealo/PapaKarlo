package com.bunbeauty.core.domain.settings

import com.bunbeauty.core.domain.repo.SettingsRepo

class UpdateEmailUseCase(
    private val settingsRepository: SettingsRepo,
) {
    suspend operator fun invoke(email: String?): Boolean {
        return settingsRepository
            .updateEmail(
                email = email ?: "",
            )?.let {
                true
            } ?: false
    }
}
