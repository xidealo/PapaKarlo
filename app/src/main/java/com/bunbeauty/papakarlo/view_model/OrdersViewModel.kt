package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import com.bunbeauty.papakarlo.view_model.base.ToolbarViewModel
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