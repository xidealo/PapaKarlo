package com.bunbeauty.papakarlo.feature.order.screen.orderlist

import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.shared.presentation.order_list.OrderListState

data class OrderListUi(
    val orderList: List<OrderItem> = emptyList(),
    val state: OrderListState.State = OrderListState.State.LOADING
)
