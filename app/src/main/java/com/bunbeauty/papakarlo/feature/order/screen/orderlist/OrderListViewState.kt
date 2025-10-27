package com.bunbeauty.papakarlo.feature.order.screen.orderlist

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.shared.presentation.base.BaseViewState

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
