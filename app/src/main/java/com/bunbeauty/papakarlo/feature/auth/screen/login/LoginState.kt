package com.bunbeauty.papakarlo.feature.auth.screen.login

import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection

data class LoginState(
    val phone: String = "",
    val hasPhoneError: Boolean = false,
    val state: State = State.Success,
    val eventList: List<Event> = emptyList(),
) {

    sealed class State {
        object Success : State()
        object Loading : State()
        data class Error(val throwable: Throwable) : State()
    }

    sealed interface Event {
        data class NavigateToConfirmEvent(
            val phone: String,
            val successLoginDirection: SuccessLoginDirection,
        ) : Event

        object NavigateBackToProfileEvent : Event
        object NavigateToCreateOrderEvent : Event
        data class SendCodeEvent(val phone: String) : Event
        data class ShowErrorEvent(val error: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
