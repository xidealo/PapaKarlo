package com.bunbeauty.address.presentation.user_address_list

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.address.SelectableUserAddress

interface UserAddressListDataState {
    data class DataState(
        val userAddressList: List<SelectableUserAddress> = emptyList(),
        val state: State,
    ) : BaseDataState {
        enum class State {
            SUCCESS,
            ERROR,
            EMPTY,
            LOADING,
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action

        data object BackClicked : Action

        data object OnClickedCreateAddress : Action

        data object OnRefreshClicked : Action
    }

    sealed interface Event : BaseEvent {
        object OpenCreateAddressEvent : Event

        object GoBackEvent : Event
    }
}
