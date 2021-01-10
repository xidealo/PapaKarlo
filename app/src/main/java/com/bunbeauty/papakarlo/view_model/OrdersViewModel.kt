package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrdersViewModel @Inject constructor(private val orderRepo: OrderRepo) : BaseViewModel() {

    val orderWithCartProductsLiveData by lazy {
        orderRepo.getOrdersWithCartProducts()
    }


    /*fun deleteAll(orderList: List<OrderWithCartProducts>) {
        viewModelScope.launch {
            orderRepo.deleteAll(orderList)
        }
    }*/
}