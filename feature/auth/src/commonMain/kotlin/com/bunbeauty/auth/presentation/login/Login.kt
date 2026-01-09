package com.bunbeauty.auth.presentation.login

import com.bunbeauty.core.Constants.PHONE_CODE
import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.base.BaseViewDataState

interface Login {
    data class ViewDataState(
        val phoneNumber: String = PHONE_CODE,
        val phoneNumberCursorPosition: Int = PHONE_CODE.length,
        val hasPhoneError: Boolean = false,
        val isLoading: Boolean = true,
    ) : BaseViewDataState

    sealed interface Action : BaseAction {
        data object Init : Action

        data class ChangePhoneNumber(
            val phoneNumber: String,
            val cursorPosition: Int,
        ) : Action

        data object NextClick : Action

        data object BackClick : Action
    }

    sealed interface Event : BaseEvent {
        data class NavigateToConfirm(
            val phoneNumber: String,
        ) : Event

        data object ShowTooManyRequestsError : Event

        data object ShowSomethingWentWrongError : Event

        data object NavigateBack : Event
    }
}
