package com.bunbeauty.profile.ui.screen.city.screen.selectcity

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.core.model.city.City
import com.bunbeauty.profile.presentation.selectcity.SelectCityDataState

@Immutable
data class SelectCityViewState(
    val state: State,
    val cityList: List<City> = emptyList(),
    val contentMode: SelectCityDataState.DataState.ContentMode = SelectCityDataState.DataState.ContentMode.CityList,
) : BaseViewState {
    @Immutable
    sealed interface State {
        object Loading : State

        object Success : State

        object Error : State
    }
}
