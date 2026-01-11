package com.bunbeauty.order.ui.screen.orderlist

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.order.ui.model.OrderItem

@Immutable
data class OrderListViewState(
    val state: State,
    val orderList: List<OrderItem> = emptyList(),
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State

        data object Error : State

        data object Success : State

        data object Empty : State
    }
}
