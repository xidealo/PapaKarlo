package com.bunbeauty.papakarlo.feature.order.screen.order_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.order.screen.order_list.OrderListFragmentDirections.toOrderDetailsFragment
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val orderUIMapper: IOrderUIMapper,
    private val orderInteractor: IOrderInteractor,
    private val userInteractor: IUserInteractor,
) : BaseViewModel() {

    private val mutableOrderListState: MutableStateFlow<State<List<OrderItem>>> =
        MutableStateFlow(State.Loading())
    val orderListState: StateFlow<State<List<OrderItem>>> =
        mutableOrderListState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {
        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                orderInteractor.observeOrderList().onEach { orderList ->
                    mutableOrderListState.value = orderList.map(orderUIMapper::toItem).toState()
                }.launchIn(this)
            }
        }
    }

    fun onOrderClicked(orderItem: OrderItem) {
        router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
    }

}