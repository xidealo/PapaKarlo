package com.bunbeauty.papakarlo.feature.profile.screen.settings

import com.bunbeauty.shared.presentation.base.BaseViewState

data class SettingsViewState(
    val phoneNumber: String,
    val selectedCityName: String,
    val state: State
) : BaseViewState {
    enum class State {
        Success,
        Loading,
        Error
    }
}
