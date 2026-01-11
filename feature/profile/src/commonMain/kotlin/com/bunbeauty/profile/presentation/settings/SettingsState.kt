package com.bunbeauty.profile.presentation.settings

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.Settings
import com.bunbeauty.core.model.city.City

interface SettingsState {
    data class DataState(
        val settings: Settings? = null,
        val selectedCity: City? = null,
        val cityList: List<City> = emptyList(),
        val state: State = State.LOADING,
        val eventList: List<Event> = emptyList(),
        val isShowLogoutBottomSheet: Boolean = false,
        val isShowCityListBottomSheet: Boolean = false,
    ) : BaseDataState {
        enum class State {
            SUCCESS,
            ERROR,
            LOADING,
        }
    }

    sealed interface Event : BaseEvent {
        data object ShowEmailChangedSuccessfullyEvent : Event

        data object ShowEmailChangingFailedEvent : Event

        data object Back : Event
    }

    sealed interface Action : BaseAction {
        data object OnCityClicked : Action

        data object CloseCityListBottomSheet : Action

        data object OnLogoutClicked : Action

        data object OnLogoutConfirmClicked : Action

        data object LoadData : Action

        data object BackClick : Action

        data object CloseLogoutBottomSheet : Action

        data class OnCitySelected(
            val cityUuid: String,
        ) : Action
    }
}
