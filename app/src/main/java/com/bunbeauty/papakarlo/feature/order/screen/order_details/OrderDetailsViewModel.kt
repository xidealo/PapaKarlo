package com.bunbeauty.papakarlo.feature.order.screen.order_details

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.order.model.OrderUI
import com.bunbeauty.papakarlo.feature.profile.screen.profile.OrderItemMapper
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDetailsViewModel(
    private val orderInteractor: IOrderInteractor,
    private val orderUIMapper: OrderItemMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    init {
        savedStateHandle.get<String>("orderUuid")?.let { orderUuid ->
            observeOrder(orderUuid)
        }
    }

    private fun observeOrder(orderUuid: String) {
        orderInteractor.observeOrderByUuid(orderUuid).launchOnEach { orderWithAmounts ->
            orderWithAmounts?.let {
                mutableOrderState.value = orderUIMapper.toOrderUI(orderWithAmounts).toState()
            }
        }
    }
}
