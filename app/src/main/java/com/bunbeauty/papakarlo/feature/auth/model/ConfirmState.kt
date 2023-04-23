package com.bunbeauty.papakarlo.feature.auth.model

import com.google.firebase.auth.PhoneAuthProvider

data class ConfirmState(
    val phoneNumber: String,
    val resendToken: PhoneAuthProvider.ForceResendingToken,
    val verificationId: String,
    val resendSeconds: Int,
    val isCodeChecking: Boolean,
    val eventList: List<Event> = emptyList(),
) {
    val isResendEnable: Boolean = resendSeconds == 0
    val formattedPhoneNumber: String = phoneNumber.replace(Regex("[\\s()-]"), "")

    sealed interface Event {
        class ShowErrorMessageEvent(val error: ConfirmError) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())

    enum class ConfirmError {
        SOMETHING_WENT_WRONG_ERROR,
        WRONG_CODE_ERROR,
    }
}
