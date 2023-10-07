package com.bunbeauty.shared.presentation.confirm

import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseState

interface Confirm {

    data class State(
        val phoneNumber: String,
        val resendSeconds: Int,
        val isLoading: Boolean,
    ): BaseState {
        val isResendEnable: Boolean = resendSeconds == 0
    }

    sealed interface Event: BaseEvent {

        data object ShowTooManyRequestsError : Event
        data object ShowNoAttemptsError : Event
        data object ShowInvalidCodeError : Event
        data object ShowAuthSessionTimeoutError : Event
        data object ShowSomethingWentWrongError : Event
        data object NavigateBackToProfile : Event
        data object NavigateToCreateOrder : Event
        data object NavigateBack : Event
    }

    sealed interface Action: BaseAction {
        data class Init(val phoneNumber: String, val direction: SuccessLoginDirection): Action
        data class CheckCode(val code: String): Action
        data object ResendCode: Action
        data object BackClick : Action
    }

}