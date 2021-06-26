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


abstract class OrderViewModel : BaseViewModel() {
    abstract val orderState: StateFlow<State<Order?>>
    abstract val delivery: Delivery
    abstract fun getOrder(orderUuid: String)
}

class OrderViewModelImpl @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreHelper: IDataStoreHelper
) : OrderViewModel() {

    override val delivery: Delivery
        get() = runBlocking {
            dataStoreHelper.delivery.first()
        }

    override val orderState: MutableStateFlow<State<Order?>> =
        MutableStateFlow(State.Loading())

    override fun getOrder(orderUuid: String) {
        orderRepo.getOrderWithCartProducts(orderUuid).onEach { order ->
            if (order != null)
                orderState.value = order.toStateNullableSuccess()
            else
                orderState.value = State.Empty()
        }.launchIn(viewModelScope)
    }
}