package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrdersFragmentDirections.toOrderBottomSheet
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    @Api private val orderRepo: OrderRepo,
    private val orderUIMapper: IOrderUIMapper,
) : BaseViewModel() {

    init {
        subscribeOnOrders()
    }

    private val mutableOrdersState: MutableStateFlow<State<List<OrderItem>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderItem>>> = mutableOrdersState.asStateFlow()

    private fun subscribeOnOrders() {
        orderRepo.observeOrderList().onEach { orderList ->
            if (orderList.isEmpty()) {
                mutableOrdersState.value = State.Empty()
            } else {
                mutableOrdersState.value = orderList.map(orderUIMapper::toItem).toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    fun onOrderClicked(orderItem: OrderItem) {
        router.navigate(toOrderBottomSheet(orderItem.uuid, orderItem.code))
    }

}