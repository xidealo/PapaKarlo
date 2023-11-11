package com.bunbeauty.shared.presentation.login

import com.bunbeauty.shared.Constants.PHONE_CODE
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

interface Login {

    data class ViewDataState(
        val phoneNumber: String = PHONE_CODE,
        val phoneNumberCursorPosition: Int = PHONE_CODE.length,
        val hasPhoneError: Boolean = false,
        val isLoading: Boolean = false,
    ): BaseViewDataState

    sealed interface Action: BaseAction {
        data object Init : Action

        data class ChangePhoneNumber(
            val phoneNumber: String,
            val cursorPosition: Int,
        ) : Action

        data object NextClick : Action
        data object BackClick : Action
    }

    sealed interface Event: BaseEvent {
        data class NavigateToConfirm(val phoneNumber: String) : Event
        data object ShowTooManyRequestsError : Event
        data object ShowSomethingWentWrongError : Event
        data object NavigateBack : Event
    }
}
