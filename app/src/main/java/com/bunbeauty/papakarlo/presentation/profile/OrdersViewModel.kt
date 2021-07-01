package com.bunbeauty.papakarlo.presentation.profile

import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import javax.inject.Inject

class OrdersViewModel @Inject constructor(orderRepo: OrderRepo) : ToolbarViewModel() {

    val orderWithCartProductsLiveData = orderRepo.getOrdersWithCartProducts()

    fun onOrderClicked(order: Order) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(order.orderEntity.uuid))
    }
}