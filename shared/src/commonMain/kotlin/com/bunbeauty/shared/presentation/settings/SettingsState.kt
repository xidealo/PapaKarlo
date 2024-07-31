package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.model.city.City

data class SettingsState(
    val settings: Settings? = null,
    val selectedCity: City? = null,
    val cityList: List<City> = emptyList(),
    val state: State = State.LOADING,
    val eventList: List<Event> = emptyList()
) {

    sealed interface Event {
        class ShowEditEmailEvent(val email: String?) : Event
        data object ShowLogoutEvent : Event
        class ShowCityListEvent(val cityList: List<City>, val selectedCityUuid: String?) : Event
        data object ShowEmailChangedSuccessfullyEvent : Event
        data object ShowEmailChangingFailedEvent : Event
        data object Back : Event
    }

    enum class State {
        SUCCESS,
        ERROR,
        LOADING
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
