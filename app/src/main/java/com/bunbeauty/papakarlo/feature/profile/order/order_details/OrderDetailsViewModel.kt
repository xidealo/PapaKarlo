package com.bunbeauty.papakarlo.feature.profile.order.order_details

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDetailsViewModel(
    private val orderInteractor: IOrderInteractor,
    private val orderUIMapper: IOrderUIMapper,
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
                mutableOrderState.value =
                    orderUIMapper.toOrderUI(orderWithAmounts).toSuccessOrEmpty()
            }
        }
    }
}