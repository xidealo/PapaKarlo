package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.order_details.OrderDetails

class OrderDetailsUiStateMapper(
    private val stringUtil: IStringUtil,
    private val orderProductItemMapper: OrderProductItemMapper,
    private val paymentMethodUiStateMapper: PaymentMethodUiStateMapper,
) {
    fun map(orderState: OrderDetails.DataState): OrderDetailsViewState =
        OrderDetailsViewState(
            orderProductItemList =
                orderState.orderDetailsData.orderProductItemList.map(
                    orderProductItemMapper::toItem,
                ),
            deliveryCost = orderState.orderDetailsData.deliveryCost,
            newTotalCost = orderState.orderDetailsData.newTotalCost,
            orderInfo =
                orderState.orderDetailsData.orderInfo?.let { orderInfo ->
                    OrderDetailsViewState.OrderInfo(
                        status = orderInfo.status,
                        statusName =
                            stringUtil.getOrderStatusName(
                                orderInfo.status,
                            ),
                        dateTime = stringUtil.getDateTimeString(orderInfo.dateTime),
                        deferredTime = stringUtil.getTimeString(orderInfo.deferredTime),
                        address = stringUtil.getOrderAddressString(orderInfo.address),
                        comment = orderInfo.comment,
                        pickupMethod = stringUtil.getPickupMethodString(orderInfo.isDelivery),
                        deferredTimeHint = stringUtil.getDeferredString(orderInfo.isDelivery),
                        paymentMethod =
                            orderInfo.paymentMethod?.let { paymentMethod ->
                                paymentMethodUiStateMapper.map(paymentMethod)
                            },
                    )
                },
            code = orderState.orderDetailsData.orderInfo?.code ?: "",
            discount = orderState.orderDetailsData.discount,
            state = orderState.screenState,
            orderUuid = orderState.orderUuid,
        )
}
