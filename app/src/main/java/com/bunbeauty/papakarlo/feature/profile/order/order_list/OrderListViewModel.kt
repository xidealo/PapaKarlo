package com.bunbeauty.papakarlo.feature.profile.order.order_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toStateSuccess
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderListFragmentDirections.toOrderDetailsFragment
import com.bunbeauty.papakarlo.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderListViewModel  constructor(
    private val orderUIMapper: IOrderUIMapper,
    private val orderInteractor: IOrderInteractor,
    private val userInteractor: IUserInteractor,
) : BaseViewModel() {

    private val mutableOrderListState: MutableStateFlow<State<List<OrderItemModel>>> =
        MutableStateFlow(State.Loading())
    val orderListState: StateFlow<State<List<OrderItemModel>>> = mutableOrderListState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {
        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                orderInteractor.observeOrderList().onEach { orderList ->
                    if (orderList.isEmpty()) {
                        mutableOrderListState.value = State.Empty()
                    } else {
                        mutableOrderListState.value =
                            orderList.map(orderUIMapper::toItem).toStateSuccess()
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onOrderClicked(orderItem: OrderItemModel) {
        router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
    }

}