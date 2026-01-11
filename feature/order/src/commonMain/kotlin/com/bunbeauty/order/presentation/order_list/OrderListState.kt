package com.bunbeauty.order.presentation.order_list

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.order.LightOrder

interface OrderListState {
    data class DataState(
        val orderList: List<LightOrder> = emptyList(),
        val state: State = State.LOADING,
    ) : BaseDataState {
        enum class State {
            SUCCESS,
            LOADING,
            ERROR,
            EMPTY,
        }
    }

    sealed interface Action : BaseAction {
        data object BackClicked : Action

        data object OnRefreshClicked : Action

        data class OnOrderClicked(
            val uuid: String,
        ) : Action

        data object StartObserveOrder : Action

        data object StopObserveOrder : Action
    }

    sealed interface Event : BaseEvent {
        data object GoBackEvent : Event

        data class OpenOrderDetailsEvent(
            val orderUuid: String,
        ) : Event
    }
}
