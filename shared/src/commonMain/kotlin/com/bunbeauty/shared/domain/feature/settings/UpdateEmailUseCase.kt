package com.bunbeauty.shared.domain.feature.settings

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.SettingsRepository

class UpdateEmailUseCase(
    private val settingsRepository: SettingsRepository,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(email: String?): Boolean =
        dataStoreRepo.getToken()?.let { token ->
            settingsRepository
                .updateEmail(
                    email = email ?: "",
                    token = token,
                )?.let {
                    true
                } ?: false
        } ?: false
}
