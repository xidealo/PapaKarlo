package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.mapper.SettingsMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.model.Settings
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val dataStoreRepo: DataStoreRepo,
    private val networkConnector: NetworkConnector,
    private val settingsMapper: SettingsMapper
) : BaseRepository() {

    override val tag: String = "SETTINGS_TAG"

    suspend fun observeSettings(token: String): Flow<Settings?> {
        networkConnector.getSettings(token).getNullableResult { settingsServer ->
            dataStoreRepo.saveSettings(settingsMapper.toSettings(settingsServer))
        }
        return dataStoreRepo.settings
    }

    suspend fun updateEmail(email: String, token: String): Settings? {
        return networkConnector.patchSettings(token, PatchUserServer(email = email)).getNullableResult { settingsServer ->
            val settings = settingsMapper.toSettings(settingsServer)
            dataStoreRepo.saveSettings(settings)
            settings
        }
    }
}
