package com.bunbeauty.shared.presentation.order_list

import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent


interface OrderListState {
    data class DataState(
        val orderList: List<LightOrder>? = null,
        val state: State = State.LOADING
    ) : BaseDataState {

        enum class State {
            SUCCESS,
            LOADING,
            ERROR,
            EMPTY
        }
    }
    sealed interface Action: BaseAction {
        data object Init : Action
        data object BackClicked : Action
        data object OnRefreshClicked : Action
        data class onOrderClicked(val uuid: String) : Action
    }

    sealed interface Event: BaseEvent {
        data object GoBackEvent : Event
        data class OpenOrderDetailsEvent(val orderUuid: String) : Event
    }
}
