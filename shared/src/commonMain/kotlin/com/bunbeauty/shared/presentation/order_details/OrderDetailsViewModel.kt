package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.feature.order.ObserveOrderUseCase
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val orderDetailsMapper: OrderDetailsMapper,
) : SharedViewModel() {

    private val mutableOrderState = MutableStateFlow(OrderDetailsState())
    val orderState = mutableOrderState.asStateFlow()

    var observeOrderJob: Job? = null

    fun loadOrder(orderUuid: String) {
        observeOrderJob?.cancel()
        mutableOrderState.update { state ->
            state.copy(isLoading = true)
        }
        observeOrderJob = sharedScope.launch {
            observeOrderUseCase(orderUuid).collectLatest { order ->
                if (order != null) {
                    mutableOrderState.update { state ->
                        state.copy(
                            orderDetailsList = orderDetailsMapper.toOrderDetails(order),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}
