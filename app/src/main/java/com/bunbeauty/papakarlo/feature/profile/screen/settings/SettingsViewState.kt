package com.bunbeauty.papakarlo.feature.profile.screen.settings

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.base.BaseViewState

data class SettingsViewState(
    val phoneNumber: String,
    val selectedCityName: String,
    val state: State,
    val logoutUI: LogoutUI
) : BaseViewState {

    @Immutable
    data class LogoutUI(
        val isShown: Boolean
    )

    enum class State {
        Success,
        Loading,
        Error
    }
}
