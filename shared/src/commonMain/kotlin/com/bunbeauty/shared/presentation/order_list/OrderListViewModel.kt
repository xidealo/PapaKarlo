package com.bunbeauty.shared.presentation.order_list

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.order.ObserveOrderListUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val observeOrderListUseCase: ObserveOrderListUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
) : SharedViewModel() {

    private val mutableOrderListState = MutableStateFlow(OrderListState())
    val orderListState = mutableOrderListState.asCommonStateFlow()

    private var orderObservationUuid: String? = null
    private var observeOrdersJob: Job? = null

    fun onOrderClicked(order: LightOrder) {
        mutableOrderListState.update { state ->
            state + OrderListState.OpenOrderDetailsEvent(order.uuid, order.code)
        }
    }

    fun consumeEvents(eventList: List<OrderListState.Event>) {
        mutableOrderListState.update { state ->
            state - eventList
        }
    }

    fun observeOrders() {
        observeOrdersJob = sharedScope.launch {
            val (uuid, orderListFlow) = observeOrderListUseCase()
            orderObservationUuid = uuid
            orderListFlow.collectLatest { orderList ->
                mutableOrderListState.update { state ->
                    state.copy(
                        orderList = orderList,
                        state = if (orderList.isEmpty())
                            OrderListState.State.EMPTY
                        else
                            OrderListState.State.SUCCESS
                    )
                }
            }
        }
    }

    fun stopObserveOrders() {
        observeOrdersJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launch {
                stopObserveOrdersUseCase(uuid)
            }
        }
        orderObservationUuid = null
    }
}
