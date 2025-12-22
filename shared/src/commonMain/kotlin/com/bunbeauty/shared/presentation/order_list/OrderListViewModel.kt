package com.bunbeauty.shared.presentation.order_list

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.domain.feature.order.ObserveOrderListUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val observeOrderListUseCase: ObserveOrderListUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
) : SharedStateViewModel<OrderListState.DataState, OrderListState.Action, OrderListState.Event>(
        initDataState = OrderListState.DataState(),
    ) {
    private var orderObservationUuid: String? = null
    private var observeOrdersJob: Job? = null

    override fun reduce(
        action: OrderListState.Action,
        dataState: OrderListState.DataState,
    ) {
        when (action) {
            OrderListState.Action.BackClicked -> onBackClicked()
            OrderListState.Action.OnRefreshClicked -> observeOrders()
            is OrderListState.Action.OnOrderClicked ->
                onOrderClicked(
                    uuid = action.uuid,
                )

            OrderListState.Action.StartObserveOrder -> observeOrders()
            OrderListState.Action.StopObserveOrder -> stopObserveOrders()
        }
    }

    private fun onBackClicked() {
        addEvent {
            OrderListState.Event.GoBackEvent
        }
    }

    private fun onOrderClicked(uuid: String) {
        addEvent {
            OrderListState.Event.OpenOrderDetailsEvent(uuid)
        }
    }

    private fun observeOrders() {
        observeOrdersJob =
            sharedScope.launchSafe(
                block = {
                    val (uuid, orderListFlow) = observeOrderListUseCase()
                    orderObservationUuid = uuid
                    orderListFlow.collectLatest { orderList ->
                        setState {
                            copy(
                                orderList = orderList,
                                state =
                                    if (orderList.isEmpty()) {
                                        OrderListState.DataState.State.EMPTY
                                    } else {
                                        OrderListState.DataState.State.SUCCESS
                                    },
                            )
                        }
                    }
                },
                onError =
                    { error ->
                        Logger.logE("OrderList", error.stackTraceToString())
                    },
            )
    }

    private fun stopObserveOrders() {
        observeOrdersJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launch {
                stopObserveOrdersUseCase(uuid)
            }
        }
        orderObservationUuid = null
    }
}
