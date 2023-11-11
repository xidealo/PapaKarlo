package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.order.ObserveOrderUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
) : SharedViewModel() {

    private val mutableOrderState = MutableStateFlow(
        OrderDetailsState(discount = null)
    )
    val orderState = mutableOrderState.asCommonStateFlow()

    private var observeOrderJob: Job? = null
    private var orderObservationUuid: String? = null

    fun loadOrder(orderUuid: String) {
        mutableOrderState.update { state ->
            state.copy(isLoading = true)
        }
        observeOrderJob = sharedScope.launch {
            val (uuid, orderFlow) = observeOrderUseCase(orderUuid)
            orderObservationUuid = uuid
            orderFlow.collectLatest { order ->
                if (order != null) {
                    mutableOrderState.update { state ->
                        state.copy(
                            orderProductItemList = getProductList(order),
                            orderInfo = getOrderInfo(order),
                            deliveryCost = order.deliveryCost?.toString(),
                            oldTotalCost = order.oldTotalCost?.toString(),
                            newTotalCost = order.newTotalCost.toString(),
                            isLoading = false,
                            discount = order.percentDiscount?.let { discount ->
                                discount.toString() + PERCENT
                            }
                        )
                    }
                }
            }
        }
    }

    fun stopObserveOrders() {
        observeOrderJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launch {
                stopObserveOrdersUseCase(uuid)
            }
        }
        orderObservationUuid = null
    }

    private fun getOrderInfo(order: Order) =
        OrderDetailsState.OrderInfo(
            code = order.code,
            status = order.status,
            dateTime = order.dateTime,
            deferredTime = order.deferredTime,
            address = order.address,
            comment = order.comment,
            isDelivery = order.isDelivery,
            paymentMethod = order.paymentMethod
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
                count = orderProduct.count.toString(),
            )
        }
}
