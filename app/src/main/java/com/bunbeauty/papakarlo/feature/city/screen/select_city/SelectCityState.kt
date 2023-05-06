package com.bunbeauty.papakarlo.feature.city.screen.select_city

import com.bunbeauty.shared.domain.model.city.City

data class SelectCityDataState(
    val state: State = State.LOADING,
    val cityList: List<City>? = null,
    val eventList: List<SelectCityEvent> = emptyList()
) {

    enum class State {
        LOADING,
        SUCCESS,
        ERROR,
    }

    operator fun plus(event: SelectCityEvent) = copy(eventList = eventList + event)
    operator fun minus(events: List<SelectCityEvent>) = copy(eventList = eventList - events.toSet())
}

sealed interface SelectCityEvent {
    object NavigateToMenu : SelectCityEvent
}

data class SelectCityUIState(
    val cityListState: CityListState = CityListState.Loading,
    val eventList: List<SelectCityEvent> = emptyList()
) {

    sealed interface CityListState {

        object Loading : CityListState
        data class Success(val cityList: List<City>) : CityListState
        object Error : CityListState
    }
}
