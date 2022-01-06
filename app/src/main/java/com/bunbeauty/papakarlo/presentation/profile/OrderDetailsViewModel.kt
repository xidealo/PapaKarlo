package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toStateSuccess
import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import com.bunbeauty.presentation.model.OrderUI
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderInteractor: IOrderInteractor,
    private val orderUIMapper: IOrderUIMapper,
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    fun getOrder(orderUuid: String) {
        orderInteractor.observeOrderByUuid(orderUuid).onEach { orderDetails ->
            if (orderDetails != null) {
                mutableOrderState.value = orderUIMapper.toUI(orderDetails).toStateSuccess()
            } else {
                mutableOrderState.value = State.Empty()
            }
        }.launchIn(viewModelScope)
    }
}