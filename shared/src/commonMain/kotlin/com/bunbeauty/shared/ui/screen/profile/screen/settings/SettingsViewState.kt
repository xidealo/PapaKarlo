package com.bunbeauty.shared.ui.screen.profile.screen.settings

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.ui.screen.city.screen.CityUI
import kotlinx.collections.immutable.ImmutableList

data class SettingsViewState(
    val phoneNumber: String,
    val selectedCityName: String,
    val state: State,
    val logoutUI: LogoutBottomSheetUI,
    val cityListBottomSheetUI: CityListBottomSheetUI,
) : BaseViewState {
    @Immutable
    data class LogoutBottomSheetUI(
        val isShown: Boolean,
    )

    @Immutable
    data class CityListBottomSheetUI(
        val isShown: Boolean,
        val cityListUI: ImmutableList<CityUI>,
    )

    enum class State {
        Success,
        Loading,
        Error,
    }
}
