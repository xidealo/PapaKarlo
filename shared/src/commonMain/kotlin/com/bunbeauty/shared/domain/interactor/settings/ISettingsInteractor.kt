package com.bunbeauty.shared.domain.interactor.settings

import com.bunbeauty.shared.domain.model.profile.Settings
import kotlinx.coroutines.flow.Flow

interface ISettingsInteractor {
    suspend fun getSettings(): Settings?
    fun observeSettings(): Flow<Settings?>
}