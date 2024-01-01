package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.presentation.order_details.OrderDetails

class OrderDetailsUiStateMapper(
    private val stringUtil: IStringUtil,
    private val orderProductItemMapper: OrderProductItemMapper,
    private val paymentMethodUiStateMapper: PaymentMethodUiStateMapper,
) {
    fun map(orderState: OrderDetails.ViewDataState): OrderDetailsUi {
        return OrderDetailsUi(
            orderProductItemList = orderState.orderDetailsData.orderProductItemList.map(
                orderProductItemMapper::toItem
            ),
            oldTotalCost = orderState.orderDetailsData.oldTotalCost,
            deliveryCost = orderState.orderDetailsData.deliveryCost,
            newTotalCost = orderState.orderDetailsData.newTotalCost,
            orderInfo = orderState.orderDetailsData.orderInfo.let { orderInfo ->
                OrderDetailsUi.OrderInfo(
                    status = orderInfo?.status ?: OrderStatus.NOT_ACCEPTED,
                    statusName = stringUtil.getOrderStatusName(
                        orderInfo?.status ?: OrderStatus.NOT_ACCEPTED
                    ),
                    dateTime = orderInfo?.dateTime?.let { dateTime ->
                        stringUtil.getDateTimeString(dateTime)
                    } ?: "",
                    deferredTime = orderInfo?.deferredTime
                        ?.let { stringUtil.getTimeString(it) },
                    address = orderInfo?.address?.let { orderAddress ->
                        stringUtil.getOrderAddressString(orderAddress)
                    } ?: "",
                    comment = orderInfo?.comment,
                    pickupMethod = orderInfo?.isDelivery?.let { isDelivery ->
                        stringUtil.getPickupMethodString(isDelivery)
                    } ?: "",
                    deferredTimeHintId = if (orderInfo?.isDelivery == true) {
                        R.string.delivery_time
                    } else {
                        R.string.pickup_time
                    },
                    paymentMethod = orderInfo?.paymentMethod?.let { paymentMethod ->
                        paymentMethodUiStateMapper.map(paymentMethod)
                    }
                )
            },
            code = orderState.orderDetailsData.orderInfo?.code ?: "",
            discount = orderState.orderDetailsData.discount,
            state = orderState.screenState,
        )
    }
}
