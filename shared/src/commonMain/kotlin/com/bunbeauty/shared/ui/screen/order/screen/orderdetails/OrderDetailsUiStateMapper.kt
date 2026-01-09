package com.bunbeauty.shared.ui.screen.order.screen.orderdetails

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.presentation.order_details.OrderDetails
import com.bunbeauty.designsystem.ui.getDateTimeString
import com.bunbeauty.designsystem.ui.getDeferredString
import com.bunbeauty.designsystem.ui.getPickupMethodString
import com.bunbeauty.designsystem.ui.getTimeString
import com.bunbeauty.profile.ui.screen.profile.getOrderStatusName
import com.bunbeauty.profile.ui.screen.profile.mapToString
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.asap

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
