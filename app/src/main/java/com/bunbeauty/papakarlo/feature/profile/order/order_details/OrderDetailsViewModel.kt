package com.bunbeauty.papakarlo.feature.profile.order.order_details

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.papakarlo.common.mapper.order.IOrderUIMapper
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.state.toSuccessOrEmpty
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderInteractor: IOrderInteractor,
    private val orderUIMapper: IOrderUIMapper
) : BaseViewModel() {

    private val mutableOrderState: MutableStateFlow<State<OrderUI>> =
        MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<OrderUI>> = mutableOrderState.asStateFlow()

    private val mutableOrderStatus: MutableStateFlow<OrderUI?> = MutableStateFlow(null)
    val orderStatus: StateFlow<OrderUI?> = mutableOrderStatus.asStateFlow()

    var isOrderLoaded = false

    fun getOrder(orderUuid: String) {
        if (isOrderLoaded) {
            return
        }
        isOrderLoaded = true

        viewModelScope.launch {
            mutableOrderState.value =
                orderInteractor.getOrderByUuid(orderUuid)?.let { orderDetails ->
                    val orderUI = orderUIMapper.toUI(orderDetails)
                    mutableOrderStatus.value = orderUI
                    orderUI
                }.toSuccessOrEmpty()
        }
        orderInteractor.observeOrderByUuid(orderUuid).onEach { orderDetails ->
            if (orderDetails != null) {
                mutableOrderStatus.value = orderUIMapper.toUI(orderDetails)
            }
        }.launchIn(viewModelScope)
    }
}