package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toStateSuccess
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrdersFragmentDirections.toOrderDetailsFragment
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.mapper.order.IOrderUIMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderUIMapper: IOrderUIMapper,
    private val orderInteractor: IOrderInteractor,
    private val userInteractor: IUserInteractor,
) : BaseViewModel() {

    private val mutableOrdersState: MutableStateFlow<State<List<OrderItem>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderItem>>> = mutableOrdersState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {
        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                orderInteractor.observeOrderList().onEach { orderList ->
                    if (orderList.isEmpty()) {
                        mutableOrdersState.value = State.Empty()
                    } else {
                        mutableOrdersState.value =
                            orderList.map(orderUIMapper::toItem).toStateSuccess()
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onOrderClicked(orderItem: OrderItem) {
        router.navigate(toOrderDetailsFragment(orderItem.uuid, orderItem.code))
    }

}