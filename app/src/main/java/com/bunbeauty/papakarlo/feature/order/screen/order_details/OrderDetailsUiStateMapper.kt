package com.bunbeauty.papakarlo.feature.order.screen.order_details

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.order_details.OrderDetailsState

class OrderDetailsUiStateMapper(
    private val stringUtil: IStringUtil,
    private val orderProductItemMapper: OrderProductItemMapper,
) {
    fun map(orderState: OrderDetailsState): OrderDetailsUi {
        return OrderDetailsUi(
            orderProductItemList = orderState.orderProductItemList.map(
                orderProductItemMapper::toItem
            ),
            totalCost = orderState.totalCost?.let { totalCost ->
                stringUtil.getCostString(totalCost)
            },
            deliveryCost = orderState.deliveryCost?.let { deliveryCost ->
                stringUtil.getCostString(
                    deliveryCost
                )
            },
            finalCost = orderState.finalCost?.let { finalCost ->
                stringUtil.getCostString(
                    finalCost
                )
            },
            isLoading = orderState.isLoading,
            orderInfo = orderState.orderInfo?.let { orderInfo ->
                OrderDetailsUi.OrderInfo(
                    status = orderInfo.status,
                    statusName = stringUtil.getOrderStatusName(
                        orderInfo.status
                    ),
                    dateTime = stringUtil.getDateTimeString(orderInfo.dateTime),
                    deferredTime = orderInfo.deferredTime
                        ?.let { stringUtil.getTimeString(it) },
                    address = stringUtil.getOrderAddressString(orderInfo.address),
                    comment = orderInfo.comment,
                    pickupMethod = stringUtil.getPickupMethodString(orderInfo.isDelivery),
                    deferredTimeHintId = if (orderInfo.isDelivery) {
                        R.string.delivery_time
                    } else {
                        R.string.pickup_time
                    }
                )
            },
            code = orderState.orderInfo?.code ?: ""
        )
    }
}