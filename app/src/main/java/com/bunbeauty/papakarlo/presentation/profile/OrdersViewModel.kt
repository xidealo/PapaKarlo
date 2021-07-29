package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.entity.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import com.bunbeauty.presentation.view_model.base.adapter.OrderItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val stringUtil: IStringUtil,
    private val orderUtil: IOrderUtil,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    private val _ordersState: MutableStateFlow<State<List<OrderItem?>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderItem?>>>
        get() = _ordersState.asStateFlow()

    fun getOrders(userId: String) {
        if (userId.isNotEmpty())
            orderRepo.getOrdersWithCartProductsByUserId(userId)
                .onEach { orderWithCartProducts ->
                    if (orderWithCartProducts.isEmpty())
                        _ordersState.value = State.Empty()
                    else
                        _ordersState.value =
                            orderWithCartProducts.sortedByDescending { it.orderEntity.time }
                                .map(::toItemModel).toStateSuccess()

                }.launchIn(viewModelScope)
        else
            orderRepo.getOrdersWithCartProductsWithEmptyUserId()
                .onEach { orderWithCartProducts ->
                    if (orderWithCartProducts.isEmpty())
                        _ordersState.value = State.Empty()
                    else
                        _ordersState.value =
                            orderWithCartProducts.sortedByDescending { it.orderEntity.time }
                                .map(::toItemModel).toStateSuccess()

                }.launchIn(viewModelScope)
    }


    fun onOrderClicked(orderItem: OrderItem) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(orderItem.uuid, orderItem.code))
    }

    private fun toItemModel(order: Order): OrderItem {
        return OrderItem(
            uuid = order.orderEntity.uuid,
            orderStatus = stringUtil.toStringOrderStatus(order.orderEntity.orderStatus),
            orderColor = orderUtil.getBackgroundColor(order.orderEntity.orderStatus),
            code = order.orderEntity.code,
            time = stringUtil.toStringTime(order.orderEntity),
            deferredTime = if (order.orderEntity.deferredTime?.isNotEmpty() == true)
                "${resourcesProvider.getString(R.string.action_profile_to_time)} ${order.orderEntity.deferredTime}"
            else
                ""
        )
    }

}