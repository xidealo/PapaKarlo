package com.bunbeauty.profile.presentation.profile

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.link.Link

interface ProfileState {
    data class DataState(
        val state: State,
        val linkList: List<Link>,
        val isShowAboutAppBottomSheet: Boolean,
        val isShowFeedbackBottomSheet: Boolean,
        val appVersion: String,
    ) : BaseDataState {
        enum class State {
            AUTHORIZED,
            UNAUTHORIZED,
            ERROR,
            LOADING,
        }
    }

    sealed interface Action : BaseAction {
        data object BackClicked : Action

        data object OnRefreshClicked : Action

        data object OnYourAddressesClicked : Action

        data object OnOrderHistoryClicked : Action

        data object OnSettingsClick : Action

        data object OnLoginClicked : Action

        data object OnCafeListClicked : Action

        data object OnFeedbackClicked : Action

        data object OnAboutAppClicked : Action

        data object CloseAboutAppBottomSheet : Action

        data object CloseFeedbackBottomSheet : Action
    }

    sealed interface Event : BaseEvent {
        data object OpenSettings : Event

        data object OpenAddressList : Event

        data object OpenOrderList : Event

        data object ShowCafeList : Event

        data object OpenLogin : Event

        data object GoBackEvent : Event
    }
}
