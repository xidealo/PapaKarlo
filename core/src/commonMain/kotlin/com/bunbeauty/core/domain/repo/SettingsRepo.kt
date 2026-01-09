package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.Settings
import com.bunbeauty.core.model.city.City
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    suspend fun observeSettings(): Flow<Settings?>
    suspend fun updateEmail(
        email: String,
    ): Settings?
}
