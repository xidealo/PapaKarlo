package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.mapper.adapter.OrderAdapterMapper
import com.bunbeauty.data.mapper.firebase.OrderMapper
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderAdapterMapper: OrderAdapterMapper,
    private val dataStoreRepo: DataStoreRepo
) : ToolbarViewModel() {

    private val _ordersState: MutableStateFlow<State<List<OrderAdapterModel?>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderAdapterModel?>>>
        get() = _ordersState.asStateFlow()

    fun getOrders() {
        val userId = runBlocking { dataStoreRepo.userId.first() }
        orderRepo.getOrdersWithCartProductsByUserId(userId)
            .onEach { orderWithCartProducts ->
                if (orderWithCartProducts.isEmpty())
                    _ordersState.value = State.Empty()
                else
                    _ordersState.value =
                        orderWithCartProducts.sortedByDescending { it.orderEntity.time }
                            .map { orderAdapterMapper.from(it) }.toStateSuccess()

            }.launchIn(viewModelScope)
    }


    fun onOrderClicked(orderAdapterModel: OrderAdapterModel) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(orderAdapterModel.uuid))
    }
}