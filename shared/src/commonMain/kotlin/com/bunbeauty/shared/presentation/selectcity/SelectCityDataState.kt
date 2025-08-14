package com.bunbeauty.shared.presentation.selectcity

import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface SelectCityDataState {
    data class DataState(
        val state: State,
        val cityList: List<City> = emptyList()
    ) : BaseDataState {

        enum class State {
            LOADING,
            SUCCESS,
            ERROR
        }
    }

    sealed interface Action : BaseAction {
        data object OnRefreshClicked : Action
        data class OnCitySelected(val city: City) : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateToMenu : Event
    }
}