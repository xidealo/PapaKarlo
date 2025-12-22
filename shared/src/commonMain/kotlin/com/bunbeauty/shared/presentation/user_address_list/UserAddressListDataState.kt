package com.bunbeauty.shared.presentation.user_address_list

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

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
