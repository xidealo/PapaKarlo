package com.bunbeauty.domain.interactor.settings

import com.bunbeauty.domain.model.profile.Settings
import kotlinx.coroutines.flow.Flow

interface ISettingsInteractor {

    fun observeSettings(): Flow<Settings?>
}