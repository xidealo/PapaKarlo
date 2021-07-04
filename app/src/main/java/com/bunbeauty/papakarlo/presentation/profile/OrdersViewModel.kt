package com.bunbeauty.papakarlo.presentation.profile

import com.bunbeauty.data.mapper.adapter.OrderAdapterMapper
import com.bunbeauty.data.mapper.firebase.OrderMapper
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.OrdersFragmentDirections
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    orderRepo: OrderRepo,
    private val orderAdapterMapper: OrderAdapterMapper
) : ToolbarViewModel() {

    val orderWithCartProductsLiveData =
        orderRepo.getOrdersWithCartProducts().map { orderWithCartProducts ->
            orderWithCartProducts.sortedByDescending { it.orderEntity.time }
                .map { orderAdapterMapper.from(it) }
        }

    fun onOrderClicked(orderAdapterModel: OrderAdapterModel) {
        router.navigate(OrdersFragmentDirections.toOrderBottomSheet(orderAdapterModel.uuid))
    }
}