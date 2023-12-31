package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.domain.feature.order.ObserveOrderUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class OrderDetailsViewModel(
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
) : SharedStateViewModel<OrderDetails.ViewDataState, OrderDetails.Action, OrderDetails.Event>(
    OrderDetails.ViewDataState(
        orderDetailsData = OrderDetails.ViewDataState.OrderDetailsData(
            orderProductItemList = listOf(),
            orderInfo = null,
            oldTotalCost = null,
            deliveryCost = null,
            newTotalCost = null,
            discount = null
        ),
        screenState = OrderDetails.ViewDataState.ScreenState.LOADING,
    )
) {

    private var observeOrderJob: Job? = null
    private var orderObservationUuid: String? = null

    override fun reduce(action: OrderDetails.Action, dataState: OrderDetails.ViewDataState) {
        when (action) {
            OrderDetails.Action.Back -> addEvent {
                OrderDetails.Event.Back
            }

            is OrderDetails.Action.Init -> loadOrder(action.orderUuid)
            OrderDetails.Action.StopObserve -> stopObserveOrders()
        }
    }

    private fun loadOrder(orderUuid: String) {
        setState {
            copy(
                screenState = OrderDetails.ViewDataState.ScreenState.LOADING,
            )
        }

        observeOrderJob = sharedScope.launchSafe(
            block = {
                val (uuid, orderFlow) = observeOrderUseCase(orderUuid)
                orderObservationUuid = uuid
                orderFlow.collectLatest { order ->
                    if (order != null) {
                        setState {
                            copy(
                                orderDetailsData = OrderDetails.ViewDataState.OrderDetailsData(
                                    orderProductItemList = getProductList(order),
                                    orderInfo = getOrderInfo(order),
                                    deliveryCost = order.deliveryCost?.toString(),
                                    oldTotalCost = order.oldTotalCost?.toString(),
                                    newTotalCost = order.newTotalCost.toString(),
                                    discount = order.percentDiscount?.let { discount ->
                                        discount.toString() + PERCENT
                                    }
                                ),
                                screenState = OrderDetails.ViewDataState.ScreenState.SUCCESS,
                            )
                        }
                    }
                }
            },
            onError = {

            },
        )

    }

    private fun stopObserveOrders() {
        observeOrderJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launchSafe(
                block = {
                    stopObserveOrdersUseCase(uuid)
                },
                onError = {

                },
            )
        }
        orderObservationUuid = null
    }

    private fun getOrderInfo(order: Order) =
        OrderDetails.ViewDataState.OrderDetailsData.OrderInfo(
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
            OrderDetails.ViewDataState.OrderDetailsData.OrderProductItem(
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
                additions = orderProduct.orderAdditionList
            )
        }

}
