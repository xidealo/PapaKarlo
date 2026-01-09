package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface ProfileState {
    data class DataState(
        val lastOrder: LightOrder? = null,
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
        data object Init : Action

        data object BackClicked : Action

        data object OnRefreshClicked : Action

        data object OnYourAddressesClicked : Action

        data object OnOrderHistoryClicked : Action

        data object OnSettingsClick : Action

        data class OnLastOrderClicked(
            val uuid: String,
            val code: String,
        ) : Action

        data object OnLoginClicked : Action

        data object OnCafeListClicked : Action

        data object OnFeedbackClicked : Action

        data object OnAboutAppClicked : Action

        data object StartObserveOrder : Action

        data object StopObserveOrder : Action

        data object CloseAboutAppBottomSheet : Action

        data object CloseFeedbackBottomSheet : Action
    }

    sealed interface Event : BaseEvent {
        class OpenOrderDetails(
            val orderUuid: String,
        ) : Event

        data object OpenSettings : Event

        data object OpenAddressList : Event

        data object OpenOrderList : Event

        data object ShowCafeList : Event

        data object OpenLogin : Event

        data object GoBackEvent : Event
    }
}
