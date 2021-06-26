package com.bunbeauty.papakarlo.presentation.profile

import com.bunbeauty.data.model.order.Order
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import javax.inject.Inject

class OrdersViewModel @Inject constructor(orderRepo: OrderRepo) : ToolbarViewModel() {

    val orderWithCartProductsLiveData = orderRepo.getOrdersWithCartProducts()

    fun onOrderClicked(order: Order) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(order.orderEntity.uuid))
    }
}