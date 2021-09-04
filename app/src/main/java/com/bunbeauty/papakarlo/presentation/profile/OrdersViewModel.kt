package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.ui.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import com.bunbeauty.presentation.view_model.base.adapter.OrderItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val stringUtil: IStringUtil,
    private val authUtil: IAuthUtil,
    private val orderUtil: IOrderUtil,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    init {
        subscribeOnOrders()
    }

    private val mutableOrdersState: MutableStateFlow<State<List<OrderItem>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderItem>>> = mutableOrdersState.asStateFlow()

    private fun subscribeOnOrders() {
        val orderListFlow = orderRepo.observeOrderList() ?: return
        orderListFlow.onEach { orderList ->
            if (orderList.isEmpty()) {
                mutableOrdersState.value = State.Empty()
            } else {
                mutableOrdersState.value = orderList.map(::toItemModel).toStateSuccess()
            }
        }.launchIn(viewModelScope)
    }

    private fun toItemModel(order: Order): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.toStringOrderStatus(order.orderStatus),
            orderColor = orderUtil.getBackgroundColor(order.orderStatus),
            code = order.code,
            time = stringUtil.toStringTime(order.time),
            deferredTime = if (order.deferredTime?.isNotEmpty() == true)
                "${resourcesProvider.getString(R.string.action_profile_to_time)} ${order.deferredTime}"
            else
                ""
        )
    }

    fun onOrderClicked(orderItem: OrderItem) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(orderItem.uuid, orderItem.code))
    }

}