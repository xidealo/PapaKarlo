package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import javax.inject.Inject

class OrdersViewModel @Inject constructor(private val orderRepo: OrderRepo) : ToolbarViewModel() {

    val orderWithCartProductsLiveData by lazy {
        orderRepo.getOrdersWithCartProducts()
    }


    /*fun deleteAll(orderList: List<OrderWithCartProducts>) {
        viewModelScope.launch {
            orderRepo.deleteAll(orderList)
        }
    }*/
}