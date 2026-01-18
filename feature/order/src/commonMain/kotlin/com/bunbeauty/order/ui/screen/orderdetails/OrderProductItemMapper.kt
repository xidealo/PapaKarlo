package com.bunbeauty.order.ui.screen.orderdetails

import androidx.compose.runtime.Composable
import com.bunbeauty.core.extension.getCountString
import com.bunbeauty.order.presentation.order_details.OrderDetails

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
