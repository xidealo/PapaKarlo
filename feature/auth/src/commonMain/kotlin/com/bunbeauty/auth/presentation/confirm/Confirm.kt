package com.bunbeauty.auth.presentation.confirm

import com.bunbeauty.core.model.SuccessLoginDirection
import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.base.BaseViewDataState

interface Confirm {
    data class ViewDataState(
        val phoneNumber: String,
        val resendSeconds: Int,
        val isLoading: Boolean,
    ) : BaseViewDataState {
        val isResendEnable: Boolean = resendSeconds == 0
    }

    sealed interface Event : BaseEvent {
        data object ShowTooManyRequestsError : Event

        data object ShowNoAttemptsError : Event

        data object ShowInvalidCodeError : Event

        data object ShowAuthSessionTimeoutError : Event

        data object ShowSomethingWentWrongError : Event

        data object NavigateBackToProfile : Event

        data object NavigateToCreateOrder : Event

        data object NavigateBack : Event
    }

    sealed interface Action : BaseAction {
        data class Init(
            val phoneNumber: String,
            val direction: SuccessLoginDirection,
        ) : Action

        data class CheckCode(
            val code: String,
        ) : Action

        data object ResendCode : Action

        data object BackClick : Action
    }
}
