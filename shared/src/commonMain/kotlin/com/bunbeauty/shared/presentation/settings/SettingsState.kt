package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.shared.domain.model.Settings

data class SettingsState(
    val settings: Settings? = null,
    val selectedCity: City? = null,
    val cityList: List<City> = emptyList(),
    val state: State = State.LOADING,
    val eventList: List<Event> = emptyList()
) {

    sealed interface Event {
        class ShowEditEmailEvent(val email: String?): Event
        class ShowCityListEvent(val cityList: List<City>): Event
        object ShowEmailChangedSuccessfullyEvent: Event
        object ShowEmailChangingFailedEvent: Event
        object Back: Event
    }

    enum class State {
        SUCCESS,
        ERROR,
        LOADING,
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}

