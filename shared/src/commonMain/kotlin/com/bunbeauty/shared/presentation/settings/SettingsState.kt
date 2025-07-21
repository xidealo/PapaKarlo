package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface SettingsState {

    data class DataState(
        val settings: Settings? = null,
        val selectedCity: City? = null,
        val cityList: List<City> = emptyList(),
        val state: State = State.LOADING,
        val eventList: List<Event> = emptyList(),
        val isShowLogoutBottomSheet: Boolean = false,
    ) : BaseDataState {
        enum class State {
            SUCCESS,
            ERROR,
            LOADING
        }
    }

    sealed interface Event : BaseEvent {
        class ShowCityListEvent(val cityList: List<City>, val selectedCityUuid: String?) : Event
        data object ShowEmailChangedSuccessfullyEvent : Event
        data object ShowEmailChangingFailedEvent : Event
        data object Back : Event
    }

    sealed interface Action : BaseAction {
        data object OnCityClicked : Action
        data object OnLogoutClicked : Action
        data object OnLogoutConfirmClicked : Action
        data object LoadData : Action
        data object BackClick : Action
        data object CloseLogoutBottomSheet : Action
    }
}
