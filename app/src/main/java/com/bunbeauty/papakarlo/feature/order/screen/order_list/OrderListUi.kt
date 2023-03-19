package com.bunbeauty.papakarlo.feature.order.screen.order_list

import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.shared.presentation.order_list.OrderListState

data class OrderListUi(
    val orderList: List<OrderItem> = emptyList(),
    val state: OrderListState.State = OrderListState.State.LOADING,
)
