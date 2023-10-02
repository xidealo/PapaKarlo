package com.bunbeauty.papakarlo.feature.auth.screen.login

import com.bunbeauty.shared.Constants.PHONE_CODE

data class LoginState(
    val phoneNumber: String = PHONE_CODE,
    val phoneNumberCursorPosition: Int = PHONE_CODE.length,
    val hasPhoneError: Boolean = false,
    val isLoading: Boolean = false,
    val eventList: List<LoginEvent> = emptyList(),
) {

    operator fun plus(event: LoginEvent) = copy(eventList = eventList + event)
    operator fun minus(events: List<LoginEvent>) = copy(eventList = eventList - events.toSet())
}

sealed interface LoginAction {
    data object Init : LoginAction

    data class ChangePhoneNumber(
        val phoneNumber: String,
        val cursorPosition: Int,
    ) : LoginAction

    data object NextClick : LoginAction
    data class ConsumeEvents(val eventList: List<LoginEvent>) : LoginAction
    data object BackClick : LoginAction

}

sealed interface LoginEvent {

    // New
    data class NavigateToConfirmEvent(val phoneNumber: String) : LoginEvent
    data object ShowTooManyRequestsErrorEvent : LoginEvent
    data object ShowSomethingWentWrongErrorEvent : LoginEvent
    data object NavigateBack : LoginEvent
}
