package com.bunbeauty.shared.ui.screen.city.screen.selectcity

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.model.city.City
import com.bunbeauty.shared.presentation.base.BaseViewState

@Immutable
data class SelectCityViewState(
    val state: State,
    val cityList: List<City> = emptyList(),
) : BaseViewState {
    @Immutable
    sealed interface State {
        object Loading : State

        object Success : State

        object Error : State
    }
}
