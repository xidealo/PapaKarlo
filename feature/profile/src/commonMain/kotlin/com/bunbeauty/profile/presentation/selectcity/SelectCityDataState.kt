package com.bunbeauty.profile.presentation.selectcity

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.city.City

interface SelectCityDataState {
    data class DataState(
        val state: State,
        val cityList: List<City> = emptyList(),
        val contentMode: ContentMode = ContentMode.CityList,
    ) : BaseDataState {
        enum class State {
            LOADING,
            SUCCESS,
            ERROR,
        }

        enum class ContentMode {
            CityList,
            SingleCity,
        }
    }

    sealed interface Action : BaseAction {
        data object OnRefreshClicked : Action

        data class OnCitySelected(
            val city: City,
        ) : Action

        data object OnContinueClicked : Action

        data object GetCityList : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateToMenu : Event
    }
}
