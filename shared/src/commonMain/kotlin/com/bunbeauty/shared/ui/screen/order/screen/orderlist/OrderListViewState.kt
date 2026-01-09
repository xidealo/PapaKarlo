package com.bunbeauty.shared.ui.screen.order.screen.orderlist

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.shared.ui.screen.order.model.OrderItem

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
