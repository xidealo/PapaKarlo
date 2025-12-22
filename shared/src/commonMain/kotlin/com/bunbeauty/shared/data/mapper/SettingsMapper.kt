package com.bunbeauty.shared.data.mapper

import com.bunbeauty.shared.data.network.model.SettingsServer
import com.bunbeauty.shared.domain.model.Settings

class SettingsMapper {
    fun toSettings(settingsServer: SettingsServer): Settings =
        Settings(
            userUuid = settingsServer.userUuid,
            phoneNumber = settingsServer.phoneNumber,
            email = settingsServer.email,
        )
}
