package com.bunbeauty.papakarlo.feature.order.screen.orderdetails

import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.order_details.OrderDetails

class OrderProductItemMapper(
    private val stringUtil: IStringUtil,
) {

    fun toItem(orderProductItem: OrderDetails.ViewDataState.OrderDetailsData.OrderProductItem): OrderProductUiItem {
        return OrderProductUiItem(
            uuid = orderProductItem.uuid,
            name = orderProductItem.name,
            newPrice = stringUtil.getCostString(orderProductItem.newPrice),
            oldPrice = orderProductItem.oldPrice?.let { oldPrice ->
                stringUtil.getCostString(oldPrice)
            },
            newCost = stringUtil.getCostString(orderProductItem.newCost),
            oldCost = orderProductItem.oldCost?.let { oldCost ->
                stringUtil.getCostString(oldCost)
            },
            photoLink = stringUtil.getCostString(orderProductItem.photoLink),
            count = stringUtil.getCountString(orderProductItem.count),
            key = "${orderProductItem.uuid} ${
                orderProductItem.additions.joinToString(" ") { orderAddition ->
                    orderAddition.uuid
                }
            }",
            additions = orderProductItem.additions.joinToString(" • ") { orderAddition ->
                orderAddition.name
            }.ifEmpty {
                null
            }
        )
    }
}
