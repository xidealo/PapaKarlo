package com.bunbeauty.shared.ui.screen.order.screen.orderdetails

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.presentation.order_details.OrderDetails
import com.bunbeauty.shared.ui.common.getDateTimeString
import com.bunbeauty.shared.ui.common.getDeferredString
import com.bunbeauty.shared.ui.common.getOrderAddressString
import com.bunbeauty.shared.ui.common.getPickupMethodString
import com.bunbeauty.shared.ui.common.getTimeString
import com.bunbeauty.shared.ui.screen.order.ui.getOrderStatusName
import com.bunbeauty.shared.ui.screen.profile.screen.profile.mapToString
import org.jetbrains.compose.resources.stringResource
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.asap

@Composable
fun OrderDetails.DataState.mapToOrderDetailsViewState() =
    OrderDetailsViewState(
        orderProductItemList =
            orderDetailsData.orderProductItemList.map {
                it.toItem()
            },
        deliveryCost = orderDetailsData.deliveryCost,
        newTotalCost = orderDetailsData.newTotalCost,
        orderInfo =
            orderDetailsData.orderInfo?.let { orderInfo ->
                OrderDetailsViewState.OrderInfo(
                    status = orderInfo.status,
                    statusName = orderInfo.status.getOrderStatusName(),
                    dateTime = orderInfo.dateTime.getDateTimeString(),
                    deferredTime =
                        orderInfo.deferredTime?.getTimeString()
                            ?: stringResource(resource = Res.string.asap),
                    address = orderInfo.address.getOrderAddressString(),
                    comment = orderInfo.comment,
                    pickupMethod = getPickupMethodString(orderInfo.isDelivery),
                    deferredTimeHint = getDeferredString(orderInfo.isDelivery),
                    paymentMethod = orderInfo.paymentMethod?.mapToString(),
                )
            },
        code = orderDetailsData.orderInfo?.code.orEmpty(),
        discount = orderDetailsData.discount,
        state = screenState,
        orderUuid = orderUuid,
    )
