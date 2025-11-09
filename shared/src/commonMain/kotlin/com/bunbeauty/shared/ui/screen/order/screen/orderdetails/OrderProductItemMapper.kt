package com.bunbeauty.shared.ui.screen.order.screen.orderdetails

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.presentation.order_details.OrderDetails
import com.bunbeauty.shared.ui.common.ui.getCountString

@Composable
fun OrderDetails.DataState.OrderDetailsData.OrderProductItem.toItem(): OrderProductUiItem =
    OrderProductUiItem(
        uuid = uuid,
        name = name,
        newPrice = newPrice,
        newCost = newCost,
        photoLink = photoLink,
        count = getCountString(count),
        key = "OrderProduct $uuid",
        additions =
            additions
                .joinToString(" • ") { orderAddition ->
                    orderAddition.name
                }.ifEmpty {
                    null
                },
        isLast = isLast,
    )
