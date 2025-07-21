package com.bunbeauty.papakarlo.feature.profile.screen.settings

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.city.screen.CityUI
import com.bunbeauty.shared.presentation.base.BaseViewState

data class SettingsViewState(
    val phoneNumber: String,
    val selectedCityName: String,
    val state: State,
    val logoutUI: LogoutBottomSheetUI,
    val cityListBottomSheetUI: CityListBottomSheetUI
) : BaseViewState {

    @Immutable
    data class LogoutBottomSheetUI(
        val isShown: Boolean
    )

    @Immutable
    data class CityListBottomSheetUI(
        val isShown: Boolean,
        val cityListUI: List<CityUI>
    )

    enum class State {
        Success,
        Loading,
        Error
    }
}
