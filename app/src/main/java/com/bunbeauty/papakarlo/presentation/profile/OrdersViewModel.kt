package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.mapper.adapter.OrderAdapterMapper
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderAdapterMapper: OrderAdapterMapper
) : BaseViewModel() {

    private val _ordersState: MutableStateFlow<State<List<OrderAdapterModel?>>> =
        MutableStateFlow(State.Loading())
    val ordersState: StateFlow<State<List<OrderAdapterModel?>>>
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
                                .map { orderAdapterMapper.from(it) }.toStateSuccess()

                }.launchIn(viewModelScope)
        else
            orderRepo.getOrdersWithCartProductsWithEmptyUserId()
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