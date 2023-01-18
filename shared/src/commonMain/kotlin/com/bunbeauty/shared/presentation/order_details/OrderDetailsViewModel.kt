package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.feature.order.ObserveOrderUseCase
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.presentation.SharedViewModel
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val timeMapper: TimeMapper
) : SharedViewModel() {

    private val mutableOrderState = MutableStateFlow(OrderDetailsState())
    val orderState = mutableOrderState.asStateFlow()

    var observeOrderJob: Job? = null

    fun loadOrder(orderUuid: String) {
        observeOrderJob?.cancel()
        mutableOrderState.update { state ->
            state.copy(isLoading = true)
        }
        observeOrderJob = sharedScope.launch {
            observeOrderUseCase(orderUuid).collectLatest { order ->
                if (order != null) {
                    mutableOrderState.update { state ->
                        state.copy(
                            orderDetailsList = getProductList(order),
                            orderInfo = getOrderInfo(order),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getOrderInfo(order: Order) =
        OrderDetailsState.OrderInfo(
            code = order.code,
            status = order.status,
            dateTime = order.dateTime,
            deferredTime = timeMapper.toUiModel(order.deferredTime),
            address = order.address,
            comment = order.comment,
            isDelivery = order.isDelivery,
        )

    private fun getProductList(order: Order) =
        order.orderProductList.map { orderProduct ->
            OrderDetailsState.OrderProductItem(
                uuid = orderProduct.uuid,
                name = orderProduct.product.name,
                newPrice = orderProduct.product.newPrice.toString(),
                oldPrice = orderProduct.product.oldPrice?.toString(),
                newCost = (orderProduct.product.newPrice * orderProduct.count).toString(),
                oldCost = orderProduct.product.oldPrice?.let { oldPrice ->
                    (oldPrice * orderProduct.count).toString()
                },
                photoLink = orderProduct.product.photoLink,
                count = orderProduct.count,
            )
        }

}
