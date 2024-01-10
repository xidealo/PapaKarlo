package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.order_details.OrderDetails

class OrderProductItemMapper(
    private val stringUtil: IStringUtil
) {

    fun toItem(orderProductItem: OrderDetails.DataState.OrderDetailsData.OrderProductItem): OrderProductUiItem {
        return OrderProductUiItem(
            uuid = orderProductItem.uuid,
            name = orderProductItem.name,
            newPrice = orderProductItem.newPrice,
            oldPrice = orderProductItem.oldPrice,
            newCost = orderProductItem.newCost,
            oldCost = orderProductItem.oldCost,
            photoLink = orderProductItem.photoLink,
            count = stringUtil.getCountString(orderProductItem.count),
            key = "OrderProduct ${orderProductItem.uuid}",
            additions = orderProductItem.additions.joinToString(" • ") { orderAddition ->
                orderAddition.name
            }.ifEmpty {
                null
            }
        )
    }
}
