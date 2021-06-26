package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreHelper: IDataStoreHelper
) : BaseViewModel() {

    private val _orderState: MutableStateFlow<State<Order?>> = MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<Order?>>
        get() = _orderState.asStateFlow()

    val delivery: Delivery
        get() = runBlocking {
            dataStoreHelper.delivery.first()
        }

    fun getOrder(orderUuid: String) {
        orderRepo.getOrderWithCartProducts(orderUuid).onEach { order ->
            if (order != null)
                _orderState.value = order.toStateNullableSuccess()
            else
                _orderState.value = State.Empty()
        }.launchIn(viewModelScope)
    }
}