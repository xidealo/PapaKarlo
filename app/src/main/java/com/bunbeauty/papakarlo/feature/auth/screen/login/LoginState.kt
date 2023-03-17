package com.bunbeauty.papakarlo.feature.auth.screen.login

import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.google.firebase.auth.PhoneAuthProvider

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
        data class NavigateToConfirmFragment(
            val phone: String,
            val verificationId: String,
            val resendToken: PhoneAuthProvider.ForceResendingToken,
            val successLoginDirection: SuccessLoginDirection,
        ) : Event

        object NavigateBackToProfileFragment : Event
        object NavigateToCreateOrderFragment : Event
        data class SendCode(val phone: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
