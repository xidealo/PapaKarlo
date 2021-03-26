package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.OrdersFragmentDirections.toOrderBottomSheet
import javax.inject.Inject

class OrdersViewModel @Inject constructor(private val orderRepo: OrderRepo) : ToolbarViewModel() {

    val orderWithCartProductsLiveData by lazy {
        orderRepo.getOrdersWithCartProducts()
    }

    fun onOrderClicked(order:Order){
        router.navigate(toOrderBottomSheet(order))
    }
}